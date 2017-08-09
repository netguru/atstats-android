# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device
device = MonkeyRunner.waitForConnection()

# Installs the Android package.
device.installPackage('/bitrise/deploy/app-production-release-bitrise-signed.apk')

# Press 'Ok Got it' dialog
device.touch(800, 535, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(4)

# sets a variable with the package's internal name
package = 'co.netguru.android.socialslack'

# sets a variable with the name of an Activity in the package
activity = 'co.netguru.android.socialslack.feature.splash.SplashActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
device.startActivity(component=runComponent)

# Wait for the application to open
MonkeyRunner.sleep(5)
print "Application open"

# Press Sign in
device.touch(530, 1420, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(10)
print "Sing in pressed"

# Click on team field and wait for keyboard
device.touch(400, 850, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)
print "Open browser"

# Type Slack Team name
device.type("dyplom101")

# Press enter key from keyboard
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
print "Input team and next screen"
MonkeyRunner.sleep(5)

# Press on the email address field
device.touch(500, 1150, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)

# Enter email
device.type("gonzalo.acosta@netguru.co")

# Press on the password field
device.touch(500, 850, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)

# Enter password
device.type("qwerty1234")

# Press sign in
device.touch(500, 1050, MonkeyDevice.DOWN_AND_UP)
print "Input email and password and next screen"
MonkeyRunner.sleep(8)

# Swap down to authorize
for i in range(1, 10):
    device.touch(300, 500, MonkeyDevice.DOWN)
    device.touch(300, 0, MonkeyDevice.MOVE)
    print "move down"
    device.touch(300, 400, MonkeyDevice.UP)
    MonkeyRunner.sleep(0.5)

# Authorize APP
device.touch(800, 1625, MonkeyDevice.DOWN_AND_UP)
"Authorize app"
MonkeyRunner.sleep(3)

# Select Slack Social APP to open
device.touch(500, 1320, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(5)

# Click on Always
device.touch(900, 1250, MonkeyDevice.DOWN_AND_UP)
print "Select Slack Social and open app"