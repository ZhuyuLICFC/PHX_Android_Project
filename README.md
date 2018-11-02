# PHX_Android_Project
Android mobile application for PHX website: https://populationhealthexchange.org/explore-public-health/<br>
If you have some suggestions or some issue report for this project, please mail me: zhuyuli@bu.edu<br>
## 1. Repository Structure
I widely use "git branch --orphan" command to create loads of independent branches and store each project in a specific branch. Those projects are either sub-project, which inplements a specific function for the final PHX project, or test-project which is used to test some Android framework or mechanism. For each project, I will give a brief introduction here, for more detailed information such as the technical description or demo of a project, please switch to its branch and see its own readme file.<br>
Below is the basic structure of this repository:<br>
* master
	* readme
* SubProject_JsoupListViewFunction
	* MainLayoutTest/
	* README.md
* SubProject_MailsenderFunction
	* MainSender/
	* README.md
* TestProject_ActivityInteraction
	* GeoQuiz/
	* README.md
## 2. Requirement
Below is the requirement of the PHX Android Project<br>
* User downloads app from Play or Apple Store

* The first time app is opened, there is a form that must be filled out. This would duplicate the form from https://populationhealthexchange.org/newsletter-signup/, except there would be an optional check box to sign up for the newsletter. This is a Gravity Form, that we can also duplicate on our end, if it’s easier to insert into the app. Let us know. There should be some kind of mechanism for the app to remember that the form had been filled out, so that it does not pop up every time.

* The app should display:

	* Institute Information (optional if the page exists on the website, otherwise do not display)

	* Events (displays page from the website, we will create a simplified version)

	* Podcasts (option to download)

	* Webinars (view live or stream recordings, option to download recordings)*

	* Practically Speaking (option to download)*

	* Blogs (option to download)

	* My Downloads (all of the above that have the option to download; once downloaded, objects should be easy to delete from the app and from the phone)

	* Feedback (goes to email?)
	
