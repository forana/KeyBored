Try typing this anywhere while running KeyBored. (spaces are for timing - don't try to type them)

```
ffgfjh
ffgfkj
ffrljhg
LLljkj
```

[Check out the KeyBored wiki for more songs to play!](https://github.com/forana/KeyBored/wiki/Songs)

## Screenshot

![screenshot](https://github.com/forana/KeyBored/raw/development/screenshot.png?raw=true)

## Downloads

All downloads require [Java 7 (or higher)](https://www.java.com/en/download/) to be installed in order to run.

* 4.0.0
    * [universal (jar)](https://github.com/forana/KeyBored/releases/download/4.0.0/KeyBored-4.0.0.jar)
    * [OS X](https://github.com/forana/KeyBored/releases/download/4.0.0/KeyBored-4.0.0-OSX.zip)
    * [Windows](https://github.com/forana/KeyBored/releases/download/4.0.0/KeyBored-4.0.0-Windows.zip)

(OS X - I have only tested Mavericks. Windows - I have only tested Windows 7 x64. I haven't tested anything else - would appreciate pull requests to fix it if some OS isn't supported)

## Building from source

### JAR

1. Clone this repo
2. Run `gradle fatjar` (install gradle if needed)
3. Resulting jar will be in `build/libs` - this should be runnable.

(to create Eclipse project, run `gradle eclipse`)

### OS X

1. Build the jar
2. Run `ant -f bundle-osx.xml` (install ant if needed, or just run this from eclipse)
3. Resulting .app will be in `build`

### Windows

(need to have Launch4J installed in order to build)

1. Build the jar
2. Edit `bundle-windows.xml` to point to your Launch4J installation
2. Run `ant -f bundle-windows.xml` (install ant if needed, or just run this from eclipse)
3. Resulting .exe will be in `build`

## FAQ / Troubleshooting
### Isn't this a clone of a program I saw 6 years ago?
Yep! I wrote that one, too. I've been meaning to get a proper version of it out there since 2009.

### It says version 4? Where are the others?
Version 1 was a bare-bones command line Windows application that _worked_ but had many problems. Version 2 was a hackmess of java and C++ that also only ran on Windows, but was more flexible. Version 3 was a pure-java attempt when I didn't really know what I was doing, yet. That last one's on sourceforge if you really want to google it - the source for the other two is lost to time (and frankly, I'm ok with that - it was really really bad).

### I'm getting an error on OS X like "Assistive Device not enabled," help
OS X needs to have the app (or java jar launcher, or IDE, or termal app) specifically enabled for assistive devices. See [Apple's knowledgebase](http://support.apple.com/kb/HT6026) for how to do that in Mavericks.

### My antivirus thinks this is a virus/keylogger.
It isn't - check the source. It sets off some red flags because it's listening for keyboard activity globally, which is something keyloggers do.

### I closed the window, but the sounds keep happening oh my god make it stop
If your OS supports a tray (Windows and OSX do), it's minimized to the tray. Right-click the icon and select "Quit KeyBored" to make it stop.

### I have a problem that isn't listed here.
Take a look at the [issues list](https://github.com/forana/KeyBored/issues). If you search and don't see your issue already mentioned, go ahead and open a new one.

## Licenses
KeyBored itself - MIT

Dependencies:

* [Jackson](http://wiki.fasterxml.com/JacksonDownload) - Lesser GPL
* [JNativeHook](https://github.com/kwhat/jnativehook) - Lesser GPL
* [Gervill](https://java.net/projects/gervill/pages/Home) - GPL v2 with [Classpath exception](http://www.gnu.org/software/classpath/license.html)

