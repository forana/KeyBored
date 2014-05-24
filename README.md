# KeyBored
Plays notes when you press keys, no matter what program you're in.

Try typing this anywhere while running KeyBored - if you're a fan of Zelda, you might recognize the notes. (spaces are for timing - don't try to type them)

```
jle jle jleyt
erekh ghkh

jle jle jleyt
eryek ekgh

ghj kle reh
ghj kle rty

ghj kle reh
gfjh kjlk ewre tryu ty
```

## Downloads

* [SUPER alpha demo](https://github.com/forana/KeyBored/blob/builds/KeyBored-4.0.0-alpha.jar?raw=true)

## Building from source

1. Close this repo
2. Run `gradle fatjar` (install gradle if needed)
3. Resulting jar will be in `build/libs` - this should be runnable.

## FAQ
### Isn't this a clone of a program I saw 6 years ago?
Yep! I wrote that one, too. I've been meaning to get a proper version of it out there since 2009.

### It says version 4? Where are the others?
Version 1 was a bare-bones command line Windows application that _worked_ but had many problems. Version 2 was a hackmess of java and C++ that also only ran on Windows, but was more flexible. Version 3 was a pure-java attempt when I didn't really know what I was doing, yet. That last one's on sourceforge if you really want to google it - the source for the other two is lost to time (and frankly, I'm ok with that - it was really really bad).

### I'm getting an error on OS X like "Assistive Device not enabled," help
OS X needs to have the app (or java jar launcher, or IDE, or termal app) specifically enabled for assistive devices. See [Apple's knowledgebase](http://support.apple.com/kb/HT6026) for how to do that in Mavericks.

### My antivirus thinks this is a virus/keylogger
It isn't - check the source. It sets off some red flags because it's listening for keyboard activity globally, which is something keyloggers do.

## Licenses
KeyBored itself - TODO

Dependencies:

* [Jackson](http://wiki.fasterxml.com/JacksonDownload) - Lesser GPL
* [JNativeHook](https://github.com/kwhat/jnativehook) - Lesser GPL
* [Gervill](https://java.net/projects/gervill/pages/Home) - GPL v2 with [Classpath exception](http://www.gnu.org/software/classpath/license.html)
