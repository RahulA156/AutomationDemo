Feature: serviceNow login functionality

Background:
Given open the chrome browser and maximize the window
And load the service now application

Scenario: Verify_Incident with positive credential 
Given enter username as 'admin' and password as 'India@123' to login
And search incident and select all also look up icon for caller
When search as 'Abel' in lookup window and 'Abel Tuter' from results and enter short_description as 'New Updated description'
Then click on submit and search the created one and verify

Scenario: Update Existing_Incident
Given login using username as 'admin' and password as 'India@123' and enter incident and click all
And search for previous incident link
When update the description as 'Automation Test' and submit
Then verify the updated description