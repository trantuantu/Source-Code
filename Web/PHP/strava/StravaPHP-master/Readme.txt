DEMO
Demo video: https://youtu.be/nOUIJh4XMCg
USES:
This application use to render the posion of athletes when they attend in a contest via data from GPS device (https://www.strava.com)
Comparing with friend in Facebook
Updating data
Challenging them to attend in an activity (In developing)
Process:
	Get infomation from Strava
	Get mutual friends on Facebook
	Get mutual friends on Activity
	Comparing them by Map rendering
SPECS: 
Backend: PHP
Front-end: Javascript (AngularJS framework)
Database: MySQL
Technologies: OAuth, Map, Facebook API, Strava API
START PROGRAM:
1. Install XAMPP and open MySQL and Apache server
2. Import database in database folder to MySQL workbench
3. Change connection parameters in dbConnection.php
4. Run loginStrava.html file in Apache to start this program in local host
TODO:
1. Create and share challenges to Facebook and wait for another friend accept it. You can see step 1 in angularApp.js line 219. We already created unique auto-generate link when user click Create Challenge button (AngularApp.js line 219). All of 3 steps from our idea was described finely in AngularApp.js line 219
2. Processing multi-thread (background thread) when user click Sync data button. Current version is only single thread processing
3. Processing Authentication. Current Authentication version is only temporary solution
Version 1.0 27/12/2016