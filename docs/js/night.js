/**
 * Removes any "dark" class and adds a "light" class.
 * @param {*} element 
 */
function brighten(element) {
  element.classList.remove("dark");
  element.classList.add("light");
  return element;
}

/**
 * Removes any "light" class and adds a "dark" class.
 * @param {*} element 
 */
function darken(element) {
  element.classList.remove("light");
  element.classList.add("dark");
  return element;
}

/**
 * @param {*} element
 * @return True if the element's class list contains "light"
 */
function isBright(element) {
  return element.classList.contains("light");
}

/**
 * @param {*} element
 * @return True if the element's class list contains "dark"
 */
function isDark(element) {
  return element.classList.contains("dark");
}

/**
 * @param {*} element
 * @return True if the element's class list contains "checked"
 */
function isChecked(element) {
  return element.classList.contains("checked");
}

/**
 * Depending on whether the element's class list contains "checked", 
 * the element will have "checked" removed from or added to its class list.
 * @param {*} element
 * @return 
 */
function check(element) {
  if (isChecked(element)) {
    element.classList.remove("checked");
  } else {
    element.classList.add("checked");
  }
  return element;
}

/**
 * Change from light theme to dark theme.
 */
function nightMode() {
  let all = document.getElementsByTagName("*");
  for (let i = 0; i < all.length; i++) {
    if (isDark(all[i])) {
      all[i] = brighten(all[i]);
    } else if (isBright(all[i])) {
      all[i] = darken(all[i]);
    }
  }
}

/**
 * Change from dark theme to light theme.
 */
function dayMode() {
  let all = document.getElementsByTagName("*");
  for (let i = 0; i < all.length; i++) {
    if (isBright(all[i])) {
      all[i] = darken(all[i]);
    } else if (isDark(all[i])) {
      all[i] = brighten(all[i]);
    }
  }
}

/**
 * Toggle between light and dark themes.
 */
function toggleNightMode(switchId = "nightModeSwitch") {
  let nightModeSwitch = document.getElementById(switchId);
  
  if (isChecked(nightModeSwitch)) {
    nightModeSwitch.style.filter = "grayscale(0%)";
    nightMode();
  } else {
    nightModeSwitch.style.filter = "grayscale(100%)";
    dayMode();
  }

  nightModeSwitch = check(nightModeSwitch);
}