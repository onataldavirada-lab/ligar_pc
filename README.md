# Ligar PC

App Android mínimo para enviar um pacote Wake-on-LAN ao PC.

## Configuração já incluída

- Nome do app: **Ligar PC**
- MAC: `F4:B5:20:5C:1B:5E`
- Broadcast: `192.168.15.255`
- Porta: `9`

Ao abrir o app, ele envia o Magic Packet e fecha automaticamente.

## Gerar o APK sem Android Studio

1. Crie um repositório novo no GitHub.
2. Extraia este ZIP e envie **todo o conteúdo da pasta LigarPC** para a raiz do repositório.
3. No GitHub, abra a aba **Actions**.
4. Escolha **Build APK**.
5. Clique em **Run workflow**.
6. Espere o workflow terminar.
7. Abra a execução concluída.
8. Em **Artifacts**, baixe `LigarPC-APK`.
9. Extraia o ZIP baixado. Dentro dele estará `app-debug.apk`.
10. Envie o APK para o Redmi e instale.

O APK debug é assinado automaticamente e pode ser instalado normalmente.

## Uso por voz

Depois de instalado, teste no Redmi:

`Hey Google, abrir Ligar PC`

Ao abrir o aplicativo, o pacote Wake-on-LAN é enviado automaticamente.

## Observação

O Redmi precisa estar conectado à rede Wi‑Fi que alcança a rede `192.168.15.x`, e o Wake-on-LAN do PC precisa continuar habilitado.
