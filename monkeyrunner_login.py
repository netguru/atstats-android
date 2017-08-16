# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import time

# Connects to the current device
device = MonkeyRunner.waitForConnection()

# Installs the Android package.
device.installPackage('app-production-release-bitrise-signed.apk')

# sets a variable with the package's internal name
package = 'co.netguru.android.socialslack'

# sets a variable with the name of an Activity in the package
activity = 'co.netguru.android.socialslack.feature.splash.SplashActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
device.startActivity(component=runComponent)

# Wait for the application to open
print "Application open"
time.sleep(30)

# Press Sign in
device.touch(250, 680, MonkeyDevice.DOWN_AND_UP)
print "Sing in pressed"
time.sleep(30)

# Click on team field and wait for keyboard
device.touch(250, 280, MonkeyDevice.DOWN_AND_UP)
print "Open browser"
time.sleep(10)

# Type Slack Team name
device.type("dyplom101")
time.sleep(30)

# Press enter key from keyboard
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
print "Input team and next screen"
time.sleep(40)

# Press on the email address field
device.touch(250, 390, MonkeyDevice.DOWN_AND_UP)
time.sleep(10)

# Enter email
device.type("gonzalo.acosta@netguru.co")

# Press on the password field
device.touch(250, 450, MonkeyDevice.DOWN_AND_UP)
time.sleep(10)

# Enter password
device.type("qwerty1234")

# Press sign in
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
print "Input email and password and next screen"
time.sleep(30)

# Swap down to authorize
for i in range(1, 10):
    device.touch(300, 500, MonkeyDevice.DOWN)
    device.touch(300, 0, MonkeyDevice.MOVE)
    print "move down"
    device.touch(300, 400, MonkeyDevice.UP)
    time.sleep(0.5)

# Authorize APP
device.touch(350, 750, MonkeyDevice.DOWN_AND_UP)
"Authorize app"
time.sleep(30)

# Select Slack Social APP to open
device.touch(250, 700, MonkeyDevice.DOWN_AND_UP)
time.sleep(10)

# Click on Always
device.touch(430, 760, MonkeyDevice.DOWN_AND_UP)
print "Select Slack Social and open app"

time.sleep(10)