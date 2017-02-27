<!-- Mike Purzycki mkp9ug and Nathan Nayda ntn4jg-->
<?php
session_start();
?>
<!doctype html>

<html>

  <head>
    <meta http-equiv="content-type" content="text/html; charset=windows-1252">
    <title>Assignment 2</title>
    <link rel="stylesheet" href="assignment2.css" type="text/css">   

  <style>
  #banner {
  	background-image: url("http://cenblog.org/iyc-2011/files/2011/06/JE2010_HeroHR_RGB-21.jpg");
  		background-repeat: repeat;
  		background-position: center;
		border: auto;
		padding: auto;
		height: 300px;
		width: 100%;

  }
  </style>

  <style type="text/css">
	<!--
	.tab { margin-left: 40px; }
	-->
  </style>

  <style type="text/css">
	<!--
	.textboxclass { height: 200px; width: 600px; }
	-->
  </style>

  <script type="text/javascript">
     
    function changeQType(value) {
        if(value == "mc") {
        	document.getElementById("multiple_choice_form").style.display="block";
        	document.getElementById("true_false_form").style.display="none"; 
          document.getElementById("select_all_form").style.display="none"; 
        	document.getElementById("sa").style.display="none";
        }
        if(value == "tf") {
        	document.getElementById("true_false_form").style.display="block";
        	document.getElementById("multiple_choice_form").style.display="none";
        	document.getElementById("sa").style.display="none"; 
          document.getElementById("select_all_form").style.display="none"; 
        }
        if(value == "sa") {
        	document.getElementById("sa").style.display="block";
        	document.getElementById("multiple_choice_form").style.display="none";
        	document.getElementById("true_false_form").style.display="none"; 
          document.getElementById("select_all_form").style.display="none"; 
        }
        if(value == "sata") {
          document.getElementById("sa").style.display="none";
          document.getElementById("multiple_choice_form").style.display="none";
          document.getElementById("true_false_form").style.display="none";
          document.getElementById("select_all_form").style.display="block"; 

        }
      }
        function MC_addChoice() {
        
        var rows = document.getElementById("multiple_answer_choices").rows.length;
        var oRow = document.getElementById("multiple_answer_choices").insertRow(rows);
        oRow.onmouseover=function(){document.getElementById("multiple_answer_choices").clickedRowIndex=this.rowIndex;};
        var oCell1 = oRow.insertCell(0);
          var oCell2 = oRow.insertCell(1);
          var oCell3 = oRow.insertCell(2);          
          oCell1.innerHTML = "<input type=radio name=Correct_Answer value=Answer"+(rows-1)+">";
          oCell1.align = "right";
          oCell2.innerHTML = "<input type=text name=Answer"+(rows-1) +">";
          oCell3.innerHTML = "<input type=button value=\" X \" onClick=\"MC_delRow()\">";
        document.recalc();

      }

      function MC_delRow() {
          document.getElementById("multiple_answer_choices").deleteRow(document.getElementById("multiple_answer_choices").clickedRowIndex);
      }

      function SA_addChoice() {
        
        var rows = document.getElementById("select_all_choices").rows.length;
        var oRow = document.getElementById("select_all_choices").insertRow(rows);
        oRow.onmouseover=function(){document.getElementById("select_all_choices").clickedRowIndex=this.rowIndex;};
        var oCell1 = oRow.insertCell(0);
          var oCell2 = oRow.insertCell(1);
          var oCell3 = oRow.insertCell(2);          
          oCell1.innerHTML = "Correct<input type=checkbox name=Correct_Answer value=Answer"+(rows-1)+">";
          oCell1.align = "right";
          oCell2.innerHTML = "<input type=text name=Answer"+(rows-1) +">";
          oCell3.innerHTML = "<input type=button value=\" X \" onClick=\"SA_delRow()\">";
        document.recalc();

      }

      function SA_delRow() {
          document.getElementById("select_all_choices").deleteRow(document.getElementById("select_all_choices").clickedRowIndex);
      }
      function questionChange(thevalue) {
        if (thevalue=="tf") {
          document.getElementById("true_false_form").style.visibility = "visible";
        }
      }

        
      
      function submitMC() {
        var thetable = document.getElementById("multiple_answer_choices");
        var ok = true;
        if(thetable.rows[0].cells[1].firstChild.value == "") {
          ok = false;
        }
        for(var i=2; i<thetable.rows.length; i++) {
          if(thetable.rows[i].cells[1].firstChild.value == "") {
            ok = false;
          }
        }
        if(ok==false) {
          alert("Please fill out each item in the form.");
        }
        return ok;
      }

      function submitTF() {
        var thetable = document.getElementById("true_false_table");
        var ok = true;
        if(thetable.rows[0].cells[1].firstChild.value == "") {
          ok = false;
        }

        if(ok==false) {
          alert("Please enter a question.");
        }
        return ok;
      }
      
      function submitSATA() {
        var thetable = document.getElementById("select_all_choices");
        var ok = true;
        if(thetable.rows[0].cells[1].firstChild.value == "") {
          ok = false;
        }
        for(var i=2; i<thetable.rows.length; i++) {
          if(thetable.rows[i].cells[1].firstChild.value == "") {
            ok = false;
          }
        }
        if(ok==false) {
          alert("Please fill out each item in the form.");
        }
        return ok;
      }
      function submitSA() {
        var ok=true;
        if(document.getElementById("sa_question").value=="") {
          alert("Please enter a question.");
          ok=false;
        }
        if(document.getElementById("sa_answer").value=="") {
          alert("Please enter an answer.");
          ok=false;
        }
        return ok;
      }

      function loadPage() {
        var question_type = '<%= session.getAttribute("question_type") %>';
        var question = '<%= session.getAttribute("Question") %>';
        alert("question type: "+question_type);
        alert("question: "+question);
        changeQType(question_type);
        document.getElementById("Question").value = question;
      }
                 
   </script>

  </head>

  <body>

  	<center>
  		<h2>
  			<img id="banner" src="http://cenblog.org/iyc-2011/files/2011/06/JE2010_HeroHR_RGB-21.jpg" alt="Banner Image"/>
  		</h2>
<h1>By Mike Purzycki and Nathan Nayda</h1>
  	<font size ="5">
  	<h2><a name="example">Choose Question Type</a></h2>
  	
  	<font size="3">
  	<p>
  		Choose the type of question you'd like to create:
  	</p>

  	<p class="tab">
  		<b> Question Type </b>
  		<br>
  		<select id= "qtype" name="qtype" onchange="changeQType(value)">
  			<option value="mc">multiple choice</option>
  			<option value="tf">true/false</option>
  			<option value="sa">short answer</option>
        <option value="sata">Select All That Apply</option>
  		</select>
  	</p>

  	<form id="multiple_choice_form" action="formhandler2.php" method="post" onSubmit="return submitMC()"> 
          <table id="multiple_answer_choices">
            <tr>
              <td>Question: </td> 
              <td><input type="text" name="Question" /> </td>
            </tr>
            <tr>
              <td>Answer Choices:  </td>
              <td></td>
              <td><input type=button value=" + " onClick="MC_addChoice()"></td>
            </tr> 
            <tr onMouseOver="multiple_answer_choices.clickedRowIndex=this.rowIndex">
              <td align="right"><input type="radio" name="Correct_Answer" value="Answer1" checked></td>
              <td><input type="text" name="Answer1"></td>
              <td><input type=button name=dyntbl1_delRow value=" X " onClick="MC_delRow()"></td>
            </tr>

          </table>
          <input type="hidden" name="question_type" value="mc">
          <input type="submit" value="Submit" title="Submit">
          <input type="reset" value="Reset" Name="reset">

          </form>



          <form id="true_false_form" action="formhandler2.php" method="post" onSubmit="return submitTF()" style="display: none;"> 
          <table id="true_false_table">
            <tr>
              <td>Statement: </td> 
              <td><input type="text" name="Question"/> </td>
            </tr>
            <tr>
              <td></td>
              <td align="left"><input type="radio" name="Correct_Answer" value="True" checked>True</td>
            </tr>
            <tr>
              <td></td>
              <td align="left"><input type="radio" name="Correct_Answer" value="False">False</td>
            </tr>

          </table>
          <input type="hidden" name="question_type" value="tf">
          <input type="submit" value="Submit" title="Submit">
          <input type="reset" value="Reset" Name="reset">
          </form>


          <form id="select_all_form" action="formhandler2.php" onSubmit="return submitSATA()" method="post" style="display: none;"> 
          <table id="select_all_choices">
            <tr>
              <td>Question: </td> 
              <td><input type="text" name="Question"/> </td>
            </tr>
            <tr>
              <td>Answer Choices:  </td>
              <td></td>
              <td><input type=button value=" + " onClick="SA_addChoice()"></td>
            </tr> 
            <tr onMouseOver="select_all_choices.clickedRowIndex=this.rowIndex">
              <td align="right">Correct<input type="checkbox" name="Correct_Answer[]" value="Answer1"></td>
              <td><input type="text" name="Answer1"></td>
              <td><input type=button name=dyntbl1_delRow value=" X " onClick="SA_delRow()"></td>
            </tr>

          </table>
          <input type="hidden" name="question_type" value="sata">
          <button type="submit" value="Submit" title="Submit">Submit</button>
          <input type="reset" value="Reset" Name="reset">
          </form>

  	<form id= "sa" style="display: none;" method="post" onSubmit="return submitSA()" action="formhandler2.php">
  		<b> Enter short answer question </b> <br>
  		Question:<br><input id="sa_question" type="text" name="Question" style="width: 600px;"/>
  		<br>
  		Answer:<br><textarea id="sa_answer" rows="8" cols="97" name="Correct_Answer"></textarea><br>
      <input type="hidden" name="question_type" value="sa">
  		<button type="submit" value="Submit" title="Submit">Submit</button>
      <input type="reset" value="Reset" Name="reset">
  	</form>
  </center>

