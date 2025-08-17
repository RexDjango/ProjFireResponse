

function myHamburger() {
   const element = document.getElementById("myhb");
   element.classList.remove("disabletoggle");  // Remove mystyle class
   element.classList.add("enabletoggle");  // Add newone class
}

function myHamburger1() {
   const element = document.getElementById("myhb");
   element.classList.remove("enabletoggle");  // Remove mystyle class
   element.classList.add("disabletoggle");  // Add newone class
}