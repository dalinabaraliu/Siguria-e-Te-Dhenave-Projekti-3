# Siguria-e-Te-Dhenave-Projekti-3
# PGP Email Encryption – Java Console Simulation

Ky projekt simulon protokollin **Pretty Good Privacy (PGP)** duke përdorur gjuhën programuese **Java**, përmes një ndërfaqeje të thjeshtë në **console** dhe ndarjes logjike në **klient** dhe **server**. Qëllimi është të demonstrohet procesi i dërgimit dhe marrjes së mesazheve të enkriptuara dhe të nënshkruara digjitalisht, duke përdorur kriptografi asimetrike.

---

## Karakteristikat kryesore

- Gjenerim i çelësave RSA (2048-bit) për dërguesin dhe pranuesin
- Enkriptimi i mesazheve me çelësin publik të pranuesit
- Nënshkrimi digjital me çelësin privat të dërguesit
- Verifikimi i nënshkrimit me çelësin publik të dërguesit
- Dekriptimi i mesazhit me çelësin privat të pranuesit
- Komunikim i thjeshtë mes **klientit** dhe **serverit** përmes Socket
- Ndërfaqe e plotë me console dhe logime të qarta të çdo veprimi


---


## Përshkrim i shkurtër i secilës pjesë

### `Service/` – Pjesët e përbashkëta
- **KeyManager.java** – Gjeneron dhe ruan çelësa RSA në skedarë `.key`
- **CryptoService.java** – Kryen enkriptimin dhe dekriptimin e mesazheve
- **SignatureService.java** – Krijon nënshkrime digjitale dhe i verifikon ato

### `client/` – Aplikacioni i dërguesit
- **MainClient.java** – Nis aplikacionin e klientit
- **PGPClient.java** – Merr inputin nga përdoruesi, enkripton, nënshkruan dhe dërgon mesazhin te serveri. Pastaj pret përgjigjen, e dekripton dhe e verifikon.

### `server/` – Aplikacioni i pranuesit
- **MainServer.java** – Nis serverin që pranon lidhje
- **PGPServer.java** – Merr mesazhin nga klienti dhe e “dërgon” te pranuesi (simulim në të njëjtin sistem)

---

## Udhëzime për ekzekutim

### 1. Nisni serverin: 
Në njërën dritare bëjeni run klasën MainServer
### 2. Nisni klientin
Në të njëjtën kohë në dritare tjetër bëjeni run klasën MainClient


---

## Shembull i rezultateve në console
### SERVER
``` 
Email server started, waiting for messages…
New encrypted email received from sender@example.com
Email forwarded to receiver@example.com
```
### CLIENT
```
Welcome to the PGP Email Client!
Enter your email address: sender@example.com
Enter recipient email address: receiver@example.com
Generating PGP key pair...
Done.

[INFO] Sender Private Key:
MIIEvAIBADANB... (Base64 i shkurtuar)

[INFO] Sender Public Key:
MIIBIjANBgkqh... (Base64 i shkurtuar)

[INFO] Receiver Public Key:
MIIBIjANBgkqh... (Base64 i shkurtuar)

Please enter your email content below:
> Hello, this is a test email.
Encrypting and signing the email...

[INFO] Encrypted Message (Base64):
agT0Gdf9AoiUF... (Base64 i shkurtuar)

[INFO] Digital Signature (Base64):
GJfdXl2YDc4E... (Base64 i shkurtuar)

Email successfully sent to receiver@example.com
Awaiting encrypted messages...
New encrypted email received. Decrypting...
The email from sender@example.com has been successfully decrypted and verified.


```





