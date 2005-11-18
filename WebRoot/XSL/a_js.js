function A_saveTmpReporter(reporter)	{
	var rep = reporter.value;
	
	if(reporter.checked == true)	{ // && currentTmpReporters.indexOf(reporter.value)==-1
		DynamicReport.saveTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("Adding " + rep);
	}
	else	{
		DynamicReport.removeTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("removing " + rep);
	}
}

var savedHeader = "Selected Reporters:\n<br/>";
var currentTmpReporters = "";
var currentTmpReportersCount = 0;

function A_saveTmpReporter_cb(txt)	{
	//look9ing for txt["count"] and txt["reporters"]
	//reporter has been added to the list,
	//show how many we've saved,
	if(txt["count"] > -1) {
		$("reporterCount").innerHTML = txt["count"] + " reporters selected";
		currentTmpReportersCount = txt["count"];
		
		//update the running tab for overlib if this is not an init call
		currentTmpReporters = txt["reporters"];
		
		//highlight box in red with the tempReporterName if we have some waiting to be saved
		if($("tmp_prb_queryName") && txt["count"] > 0)
			$("tmp_prb_queryName").style.border = "1px solid red";
	}
	else	{
		A_clearTmpReporters_cb("");
	}
}

function A_initSaveReporter()	{
	DynamicReport.saveTmpReporter("", A_initTmpReporter_cb);
}

function A_initTmpReporter_cb(txt)	{
	A_saveTmpReporter_cb(txt);
	//now, check the ones if theyve been previously selected
	var field = document.getElementsByName('tmpReporter');
	if(field.length > 1 && currentTmpReporters != "")	{
		for (i = 0; i < field.length; i++)	{
			if(currentTmpReporters.indexOf(field[i].value) != -1 )
				field[i].checked = true ;
		}
	}
	else	{
		if(currentTmpReporters.indexOf(field.value) != -1 )
			field.checked = true;
	}
}

function A_clearTmpReporters()	{
	DynamicReport.clearTmpReporters(A_clearTmpReporters_cb);
}

function A_clearTmpReporters_cb(txt)	{
		$("reporterCount").innerHTML = "";
		currentTmpReportersCount = 0;
		currentTmpReporters = "";
		if($("tmp_prb_queryName"))
			$("tmp_prb_queryName").style.border = "1px solid";

		A_uncheckAll(document.getElementsByName('tmpReporter'));
}

function A_checkAll(field)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)	{
				field[i].checked = true ;
				A_saveTmpReporter(field[i]);
			}
		}
		else
			field.checked = true;
}

function A_uncheckAll(field)	{
	if(field.length > 1)	{
		for (i = 0; i < field.length; i++)	{
			field[i].checked = false ;
			A_saveTmpReporter(field[i]);
		}
	}
	else
		field.checked = false;
		
	$("checkAll").checked = false;
		
}

function manageCheckAll(box)	{
	if(box.checked)	{
		A_checkAll(document.getElementsByName('tmpReporter'));
	}
	else	{
		A_uncheckAll(document.getElementsByName('tmpReporter'));
	}
}

function A_saveReporters()	{
	//get the name
	var name = $("tmp_prb_queryName").value;
	//convert the overlib list to a comma seperated list
	var replaceme = "<br/>";
	var commaSepList = currentTmpReporters.replace(/<br\/>/g, ",");
	if(commaSepList.charAt(commaSepList.length-1) == ",")
		commaSepList = commaSepList.substring(0, commaSepList.length-1);
	//alert("="+commaSepList+"=");
	DynamicReport.saveReporters(commaSepList, name, A_saveReporters_cb);
}

function A_saveReporters_cb(txt)	{
	var results = txt == "pass" ? "Reporter List Saved" : "There was a problem saving your reporter list";
	alert(results); //pass | fail
	if(txt != "fail")	{
		//erase the name
		$("tmp_prb_queryName").value = "";
		//clear the sample list
		A_clearTmpReporters();
	}
	
}