package com.jarvis.ligarpc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    // Seu PC
    private static final String MAC_ADDRESS = "F4:B5:20:5C:1B:5E";
    private static final String BROADCAST_ADDRESS = "192.168.15.255";
    private static final int PORT = 9;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView text = new TextView(this);
        text.setText("Ligando o PC…");
        text.setTextSize(24);
        text.setGravity(Gravity.CENTER);
        setContentView(text);

        executor.execute(() -> {
            try {
                sendMagicPacket(MAC_ADDRESS, BROADCAST_ADDRESS, PORT);

                mainHandler.post(() -> {
                    Toast.makeText(
                        MainActivity.this,
                        "Comando enviado.",
                        Toast.LENGTH_SHORT
                    ).show();

                    // Dá tempo para o Toast aparecer e fecha o app.
                    mainHandler.postDelayed(this::finishAndRemoveTask, 900);
                });

            } catch (Exception e) {
                mainHandler.post(() -> {
                    Toast.makeText(
                        MainActivity.this,
                        "Erro ao enviar Wake-on-LAN.",
                        Toast.LENGTH_LONG
                    ).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }

    private static void sendMagicPacket(
            String macAddress,
            String broadcastAddress,
            int port
    ) throws Exception {

        byte[] mac = parseMac(macAddress);

        byte[] packetBytes = new byte[6 + 16 * mac.length];

        // FF FF FF FF FF FF
        for (int i = 0; i < 6; i++) {
            packetBytes[i] = (byte) 0xFF;
        }

        // MAC repetido 16 vezes
        for (int i = 6; i < packetBytes.length; i += mac.length) {
            System.arraycopy(mac, 0, packetBytes, i, mac.length);
        }

        InetAddress address = InetAddress.getByName(broadcastAddress);

        DatagramPacket packet = new DatagramPacket(
                packetBytes,
                packetBytes.length,
                address,
                port
        );

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            socket.send(packet);
        }
    }

    private static byte[] parseMac(String macAddress) {
        String normalized = macAddress
                .replace(":", "")
                .replace("-", "");

        if (normalized.length() != 12) {
            throw new IllegalArgumentException("MAC inválido");
        }

        byte[] mac = new byte[6];

        for (int i = 0; i < 6; i++) {
            int index = i * 2;
            mac[i] = (byte) Integer.parseInt(
                    normalized.substring(index, index + 2),
                    16
            );
        }

        return mac;
    }
}
