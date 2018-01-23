/**
 * Wait until the test condition is true or a timeout occurs. Useful for waiting
 * on a server response or for a ui change (fadeIn, etc.) to occur.
 *
 * @param testFx javascript condition that evaluates to a boolean,
 * it can be passed in as a string (e.g.: "1 == 1" or "$('#bar').is(':visible')" or
 * as a callback function.
 * @param onReady what to do when testFx condition is fulfilled,
 * it can be passed in as a string (e.g.: "1 == 1" or "$('#bar').is(':visible')" or
 * as a callback function.
 * @param timeOutMillis the max amount of time to wait. If not specified, 3 sec is used.
 */

"use strict";
function waitFor(testFx, onReady, timeOutMillis) {
    var maxtimeOutMillis = timeOutMillis ? timeOutMillis : 3000, //< Default Max Timout is 3s
        start = new Date().getTime(),
        condition = false,
        interval = setInterval(function() {
            if ( (new Date().getTime() - start < maxtimeOutMillis) && !condition ) {
                // If not time-out yet and condition not yet fulfilled
                condition = (typeof(testFx) === "string" ? eval(testFx) : testFx()); //< defensive code
				console.log(condition);
            } else {
				console.log(condition);
                if(!condition) {
                    // If condition still not fulfilled (timeout but condition is 'false')
                    console.log("'waitFor()' timeout");
                    phantom.exit(1);
                } else {
                    // Condition fulfilled (timeout and/or condition is 'true')
                    console.log("'waitFor()' finished in " + (new Date().getTime() - start) + "ms.");
                    typeof(onReady) === "string" ? eval(onReady) : onReady(); //< Do what it's supposed to do once the condition is fulfilled
                    clearInterval(interval); //< Stop this interval
                }
            }
        }, 250); //< repeat check every 250ms
};


var page = require('webpage').create();
var system = require('system');//系统对象
page.viewportSize = {width: 800, height: 400};
var _filePath = "/web/accountAnalyze/report";
var _url = system.args[1];// 系统对象第二个参数
var _batchNo = system.args[2];// 系统对象第三个参数
var _fileName = system.args[3];// 系统对象第四个参数
var _type = system.args[4];// 系统环境-第五个参数
if(_type == 'loc'){
	_filePath = "D:" + _filePath;
}
// 图片保存路径
var _fileName_1 = _filePath + "/" + _batchNo + "/" + _fileName + "_01.png";
var _fileName_2 = _filePath + "/" + _batchNo + "/" + _fileName + "_02.png";
var _fileName_3 = _filePath + "/" + _batchNo + "/" + _fileName + "_03.png";
var _fileName_4 = _filePath + "/" + _batchNo + "/" + _fileName + "_04.png";
var _fileName_5 = _filePath + "/" + _batchNo + "/" + _fileName + "_05.png";
var _fileName_6 = _filePath + "/" + _batchNo + "/" + _fileName + "_06.png";

console.log("[PhantomJS] Opening Url:", _url);
page.open(_url, function (status) {
    // Check for page load success
    if (status !== "success") {
        console.log('open page fail!');
    } else {
        console.log("success");
        // Wait for 'signin-dropdown' to be visible
        waitFor(function() {
            // Check in the page if a specific element is now visible
            return page.evaluate(function() {
				return ($("#persistFinish").val() == 'true');
            });
        }, function() {
           console.log("The sign-in dialog should be visible now.");
           
		   page.evaluate(function() {
			   $("#chartContainer_1").show();
			   $("#chartContainer_2").hide();
			   $("#chartContainer_3").hide();
			   $("#chartContainer_4").hide();
			   $("#chartContainer_5").hide();
			   $("#chartContainer_6").hide();
           });
		   window.setTimeout(function () {
			   page.render(_fileName_1);
	       }, 100);
		   
		   window.setTimeout(function () {
			   page.evaluate(function() {
				   $("#chartContainer_1").hide();
				   $("#chartContainer_2").show();
				   $("#chartContainer_3").hide();
				   $("#chartContainer_4").hide();
				   $("#chartContainer_5").hide();
				   $("#chartContainer_6").hide();
	           });
			   page.render(_fileName_2);
	       }, 200);
		   
		   window.setTimeout(function () {
			   page.evaluate(function() {
				   $("#chartContainer_1").hide();
				   $("#chartContainer_2").hide();
				   $("#chartContainer_3").show();
				   $("#chartContainer_4").hide();
				   $("#chartContainer_5").hide();
				   $("#chartContainer_6").hide();
	           });
			   page.render(_fileName_3);
	       }, 300);
		   
		   window.setTimeout(function () {
			   page.evaluate(function() {
				   $("#chartContainer_1").hide();
				   $("#chartContainer_2").hide();
				   $("#chartContainer_3").hide();
				   $("#chartContainer_4").show();
				   $("#chartContainer_5").hide();
				   $("#chartContainer_6").hide();
	           });
			   page.render(_fileName_4);
	       }, 400);
		   
		   window.setTimeout(function () {
			   page.evaluate(function() {
				   $("#chartContainer_1").hide();
				   $("#chartContainer_2").hide();
				   $("#chartContainer_3").hide();
				   $("#chartContainer_4").hide();
				   $("#chartContainer_5").show();
				   $("#chartContainer_6").hide();
	           });
			   page.render(_fileName_5);
	       }, 500);
		   
		   window.setTimeout(function () {
			   page.evaluate(function() {
				   $("#chartContainer_1").hide();
				   $("#chartContainer_2").hide();
				   $("#chartContainer_3").hide();
				   $("#chartContainer_4").hide();
				   $("#chartContainer_5").hide();
				   $("#chartContainer_6").show();
	           });
			   page.render(_fileName_6);
	           phantom.exit();
	       }, 600);
        }, 10000);//10 s
    }
});
