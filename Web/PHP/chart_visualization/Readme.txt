Import database in database folder
Open MySQL workbench and XAMPP
1. In WorkBench: Right click choose Create schema and name database is: live_score_raw_data
2. Use function Data Import / Restore and link to this folder.
3. Choose import.

This application use to render graph and compare the result of athletes in an contest
Usage:
id: List of athletes id
bid: Id of contest

=> API Return all of necessary parameters for google graph to render (JSON), see example result in Example.html in front-end