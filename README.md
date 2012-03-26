# Volume Miser

## The Problem
Android doesn't keep track of the volume difference between speaker and headphones, unlike iOS. For example, on iOS when you plug in headphones and make volume changes then unplug them it will revert the volume to what it was before headphones were connected, and vice versa, when you connect headphones the volume will be restored to what it was the last time they were plugged in. 

## The solution: Volume Miser
This little app implements the iOS-like behavior by tracking volume differences when headphones are connected and when they are not.

It's 2 main components are a Service that observes changes to the volume and stores them, and a BroadcastReceiver that listens for headphone plug/unplug actions and restores the proper volume level.

There is also an activity that allows the user to set the volumes for speaker and headphone, enable/disable the service, and enable/disable start on boot.

## The End
That's about it. There's room for improvement, like supporting bluetooth headsets, and maybe volume profiles (like car, work, etc). But as it stands it solves the problem I wanted it to, and it's available in the Android Market (Play or whatever it's called now) here: [Volume Miser](http://market.android.com/)
