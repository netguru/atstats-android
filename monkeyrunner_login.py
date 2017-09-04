# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import time
import sys
import signal

device = None
APP_PATH = sys.argv[1]
APP_PACKAGE = sys.argv[2]
SLACK_TEAM = sys.argv[3]
ACCOUNT_EMAIL = sys.argv[4]
ACCOUNT_PASSWORD = sys.argv[5]

# Connects to the current device
def execute():
    device = MonkeyRunner.waitForConnection()

    # Installs the Android package.
    device.installPackage(APP_PATH)

    # sets a variable with the package's internal name
    package = APP_PACKAGE

    # sets a variable with the name of an Activity in the package
    activity = APP_PACKAGE + '.feature.splash.SplashActivity'

    # sets the name of the component to start
    runComponent = package + '/' + activity

    # Runs the component
    device.startActivity(component=runComponent)

    # Wait for the application to open
    print "Application open"
    time.sleep(60)

    # Press Sign in
    device.touch(250, 680, MonkeyDevice.DOWN_AND_UP)
    print "Sing in pressed"
    time.sleep(60)

    # Click on team field and wait for keyboard
    device.touch(250, 280, MonkeyDevice.DOWN_AND_UP)
    print "Open browser"
    time.sleep(60)

    # Type Slack Team name
    device.type(SLACK_TEAM)
    time.sleep(60)

    # Press enter key from keyboard
    device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
    print "Input team and next screen"
    time.sleep(60)

    # Press on the email address field
    device.touch(250, 390, MonkeyDevice.DOWN_AND_UP)
    time.sleep(60)

    # Enter email
    device.type(ACCOUNT_EMAIL)

    # Press on the password field
    device.touch(250, 450, MonkeyDevice.DOWN_AND_UP)
    time.sleep(60)

    # Enter password
    device.type(ACCOUNT_PASSWORD)

    # Press sign in
    device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
    print "Input email and password and next screen"
    time.sleep(60)

    # Swap down to authorize
    for i in range(1, 10):
        device.touch(300, 500, MonkeyDevice.DOWN)
        device.touch(300, 0, MonkeyDevice.MOVE)
        print "move down"
        device.touch(300, 400, MonkeyDevice.UP)
        time.sleep(10)

    # Authorize APP
    device.touch(350, 750, MonkeyDevice.DOWN_AND_UP)
    print "Authorize app"
    time.sleep(60)

    # Select Slack Social APP to open
    device.touch(250, 650, MonkeyDevice.DOWN_AND_UP)
    time.sleep(60)

    # Click on Always
    device.touch(430, 760, MonkeyDevice.DOWN_AND_UP)
    print "Select Slack Social and open app"

    time.sleep(60)

def exit_gracefully(signum, frame):
    print "Gracefully exiting"

    signal.signal(signal.SIGINT, signal.getsignal(signal.SIGINT))
    if device is not None:
        device.shell('killall com.android.commands.monkey')
    sys.exit(1)

if __name__ == '__main__':
    signal.signal(signal.SIGINT, exit_gracefully)
    execute()