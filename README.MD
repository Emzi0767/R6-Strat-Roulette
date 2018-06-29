# R6: Strat Roulette
R6: Strat Roulette is an Android application for people who want to randomize their game a bit, or 
perhaps try something new, instead of tryharding all day and night.

It provides several components:
* **Operator Randomizer** select a random attacker and defender for you to play
* **Team Randomizer** selects 5 random attackers and defenders for you and your friends to play
* **Recruit Randomizer** randomizes loadouts for attacking and defending recruit for extra fun
* **Strat Roulette** picks a random strategy for your game from a large pool of strategies provided 
  by community members

## Requirements
The application is built for Android 4.4+, however Android 4.4 might have issues with TLS 1.2 
support, which will make it unable to download its assets. There should, however, be no issues with 
Android versions 5.0 and above.

You need to have Internet connection available during first launch, so the application can download 
its assets (icons and data). It will download approximately 2-3 MiB of data. If internet is 
available on subsequent launches, it will check for updates for its assets.

Finally, you need about 25 MiB of available space on your device, post-installation that drops down 
to about 5 MiB.

### Permissions
The application requires 4 permissions to run:
* **Internet access** to download and update its assets
* **Read external storage** to be able to load its own assets
* **Write external storage** to be able to save its own assets
* **Access network state** to check whether internet connection is available

## Installation
As the application is not (yet) available in Play Store, you will need to sideload it. First, you 
need to ensure that your device allows installing applications from outside of Play Store. To do 
that, you need to go to **Settings &gt; Security &amp; privacy &gt; Additional settings** and make 
sure that **Unknown sources** is checked.

### Installing using just the device
Open your browser and navigate to [the download page](https://github.com/Emzi0767/R6-Strat-Roulette/releases/latest).
Download **r6-strat-roulette-vx.y.apk** (where x.y is the version), and once it's downloaded, open 
it. This should launch the application installer. Simply press install, and you're done. Upon first 
launch give the application a minute or so to let it download its assets.

### Installing using a PC
You will need Android SDK platform tools for this procedure, and USB drivers for your phone. Open 
your browser and navigate to [the download page](https://github.com/Emzi0767/R6-Strat-Roulette/releases/latest).
Download **r6-strat-roulette-vx.y.apk** (where x.y is the version) to the directory where your 
**adb** binary is located. Next, open command prompt (or a terminal emulator) and navigate to the 
directory where your ADB is located. On your phone, go to **Settings *gt; Developer** and make sure 
that **USB Debugging** is checked. Connect your phone to your PC using the USB cable. In the 
terminal emulator (or command prompt) execute `adb install r6-strat-roulette-vx.y.apk` if you're on 
Windows, or `./adb install r6-strat-roulette-vx.y.apk` if you're on GNU/Linux. Once the process is 
complete, the application is installed. Upon first launch give the application a minute or so to 
let it download its assets.

## Contact
If you find bugs or other issues, or simply have suggestions, make sure to open an issue. I'll try 
to make sure that I check them as often as possible.

You can also join [my Discord server](https://discord.gg/rGKrJDR) or contact me [via reddit](https://reddit.com/u/eMZi0767).

## Source code
The source is an IntelliJ IDEA project. It requires that you have JDK 8, as well as Android SDK, 
with platform 27 installed. Gradle should take care of the rest.

## Support me
Should you wish to support me for making this application, you can send me money through one of 
these:
* **[Patreon](https://www.patreon.com/emzi0767)**
* **[Paypal](https://www.paypal.me/Emzi0767)** 
