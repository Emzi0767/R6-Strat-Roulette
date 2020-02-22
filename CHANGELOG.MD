# Changelog for R6: Strat Roulette

### v2.0.0 (release: 2020-02-22 04:14 +01:00)
- FIX: Settings no longer reopen after being closed when device orientation changes.
- FIX: Various layout optimizations.

### v2.0.0-alpha2 (release: 2020-02-22 01:12 +01:00)
- NEW: Updated icons to meet new Google specifications.

### v2.0.0-alpha1 (release: 2020-02-21 19:14 +01:00)
- NEW: Updated the application for Year 5 onwards.
- FIX: Fixed multiple crash bugs.
  - Fixed application crashing when switching device orientation in Operator Randomizer 
- NEW: Added missing credits.
  - Linked to r6-operatoricons by marcopixel in About section.
- FIX: Made credits links clickable.

### v1.4.0 (released: )
- NEW: Updated Android targets and underlying libraries

### v1.3.1 (released: 2018-08-23 21:30 +02:00)
- FIX: When the screen is turned off while Operator randomizer is up, wrong tab might be displayed
  when it's on again.

### v1.3.0 (released: 2018-08-21 21:50 +02:00)
- NEW: Merged attacker and defender randomizers back into Operator randomizer for better UX.
  - The UI is now divided into 2 tabs instead.

### v1.2.0 (released: 2018-08-21 16:50 +02:00)
- NEW: Operator randomizer was split into 2 fragments
  - There's now Attacker Randomizer and Defender Randomizer
  - Operator Randomizer is no longer available by itself
- NEW: Operator randomizers now also randomize operator loadout
  - They will select a random primary and secondary weapons, as well as gadget.
- NEW: Operator randomizers will no longer select the same operator more than once in a row.

### v1.1.2-r2 (released: 2018-07-28 07:27 +02:00)
- FIX: Remove PayPal and Patreon links, apparently Google is mad about them now
  - Why weren't they before? 🤔

### v1.1.2 (released: 2018-07-28 03:45 +02:00)
- FIX: Actually bumped requried API level to Android 5.0

### v1.1.1 (released: 2018-07-12 20:18 +02:00)
- FIX: Exiting settings via navigation bar back button now properly saves settings

### v1.1.0 (released: 2018-07-12 01:21 +02:00)
- FIX: Bumped minimum required Android version to 5.0, as 4.4 does not support modern TLS versions 
  required to download assets
- NEW: Settings menu
  - Settings allow you to control which operators are available to you
  - Operator randomization will not randomize from disabled operator pool
  - In the future, more options may be made available in here
- NEW: Support via PayPal and Patreon buttons in about pane

### v1.0.2 (released: 2018-06-30 19:31 +02:00)
- FIX: No internet connection or unreachable asset server no longer prevent aplication from being 
  used
- FIX: Windows are now longer leaked
- NEW: Application no longer requires external storage access
- NEW: The asset dialog now shows asset verification progress

### v1.0.1 (released: 2018-06-30 01:09 +02:00)
- FIX: The spinners for choosing strat roulette side and game mode are larger now
- NEW: The application will now show DEBUG BUILD in about page if using a debug build

### v1.0.0 (released: 2018-06-28)
- Initial release
