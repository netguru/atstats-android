# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device
device = MonkeyRunner.waitForConnection()

# Installs the Android package.
device.installPackage('app/build/outputs/apk/production/debug/app-production-debug.apk')

# sets a variable with the package's internal name
package = 'co.netguru.android.socialslack'

# sets a variable with the name of an Activity in the package
activity = 'co.netguru.android.socialslack.feature.splash.SplashActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
device.startActivity(component=runComponent)

# Presses the Menu button
device.press('KEYCODE_MENU', MonkeyDevice.DOWN_AND_UP)

# Takes a screenshot
result = device.takeSnapshot()

# Writes the screenshot to a file
result.writeToFile('./shot1.png','png')