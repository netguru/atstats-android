<!-- 
    Couple of points about editing:
    
    1. Keep it SIMPLE.
    2. Refer to reference docs and other external sources when possible.
    3. Remember that the file must be useful for new / external developers, and stand as a documentation basis on its own.
    4. Try to make it as informative as possible.
    5. Do not put data that can be easily found in code.
    6. Include this file on ALL branches.
-->

<!-- Put your project's name -->
# atStats

[![Build Status](https://www.bitrise.io/app/6b9ba9da8d922f79/status.svg?token=dF3EQ-0f7XKwV9V-0Z1OVw&branch=master)](https://www.bitrise.io/app/6b9ba9da8d922f79)

atStats is a mobile app that sums up and visualises your relationship with the people you talk to on Slack, based on data from the last 30 days. If you want to grab a copy for yourself just go straight to [Google Play](). However, if you are interested in the core of atStats, check out [Configuration](#configuration) section. Note that Slack account is necessary for using the app.

We at Netguru strongly believe in open-source software. atStats isn’t our only project repo where you can find the app’s full source code. Explore other [open source projects](https://www.netguru.co/resources) created by our team.

## Configuration

### Instructions

1. Clone repo at `https://github.com/netguru/atstats-android.git`
2. Register your app for slack api [here](https://api.slack.com/apps)
3. Create secret.properties file in main folder (atstats-android) and paste contents from table:

| Property         
|---------------------------|
| FabricApiKey=[1]          | 
| SlackClientId=[2]         | 
| SlackClientSecret=[3]     |
| SlackOauthRedirectUri=[4] | 

4. Fill the gap [1] with Fabric Api Key. This property is optional - if you don't want to use Fabric Crashlytics, just leave it empty
5. Fill the gap [2] with Client Id from your [slack app](https://api.slack.com/apps)
6. Fill the gap [3] with Client Secret from your [slack app](https://api.slack.com/apps)
7. Fill the gap [4] with Redirect URL you set in your [slack app](https://api.slack.com/apps)
8. Open project in Android Studio
9. That's it!

## Contribution

You're more than welcome to contribute or report an issue in case of any problems, questions or improvement proposals.

## Authors

* [Designers Team](https://dribbble.com/netguru)
    * [Maciej Kotula](https://dribbble.com/maciej_kotula)

* [Developers Team](https://github.com/netguru/slack-social-android/graphs/contributors)

    * [Gonzalo Acosta](https://github.com/GNZ)
    * [Rafał Adasiewicz](https://github.com/adasiewiczr)
    * [Filip Zych](https://github.com/navarionek)
    * [Samuel Urbanowicz](https://github.com/samiuelson)

Copyright © 2017 [Netguru](http://netguru.co).

Licensed under the [GPLv3 License](LICENSE).
