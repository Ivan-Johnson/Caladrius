# Simple Website Template

## Description
A simple and clean website template.

## Motivation
The main reason why I have created this simple website template is because I want to quickly build new websites. Additionally, I do not have any structure in the websites built before the creation of this template, so they look unorganized and unprofessional.

## Usage

#### For those who are unfamiliar with file paths:

The `./` in file paths represents the current directory.  
The `../` in file paths represents the previous directory.  

For example, if I wanted to access `picture.jpg` the `/img` directory from `index.html`, I would use the file path `./img/picture.jpg`. However, if I were to access the `/img` from an adjacent directory, such as `/css`, I would use the file path `../img/picture.jpg`.

### Colors
  
Tags with `light` or `dark` classes will change to their opposite colors when the `dayMode()` / `nightMode()` functions are called.

``` html
<div class="light"></div>
<div class="dark"></div>
```
 
Recommended tags that should use the `light` or `dark` class:  

``` html
<body>  
<img class="media">  
<div class="container">  
<section class="container">  
<details>  
<summary>  
<code>  
<th>
<td>
```

### Night Mode Implementation

``` html
<!-- Night mode style -->
<link rel="stylesheet" href="./css/night.css">

<!-- Night mode switch -->
<img src="./img/sun.svg" alt="Toggle Night Mode" title="Toggle Night Mode" class="sun" id="nightModeSwitch" onclick="toggleNightMode()">

<!-- Night mode script -->
<script src="./js/night.js"></script>
```

*Note: `toggleNightMode()`'s default id is "nightModeSwitch". To use a different id, simply specify it in the parameter, for example: `toggleNightMode('darkThemeSwitch')`*

### Widths

``` html
<div class="w-1"></div> <!-- width: 100% -->
<div class="w-2"></div> <!-- width:  50% -->
<div class="w-3"></div> <!-- width:  33% -->
<div class="w-4"></div> <!-- width:  25% -->
```

### Padding

``` html
<div class="p-25"></div> <!-- padding: 25px -->
<div class="p-10"></div> <!-- padding: 10px -->
<div class="p-5"></div>  <!-- padding:  5px -->
```

### Containers

The recommended tags for containers are `section` or `div`, depending on the content of the container.

``` html
<!-- Light-colored 33% width container -->
<section class="light container w-3">
  <header>
    <h2>Container Heading</h2>
  </header>
  <p>Container content</p>
</section>
```

### Collapsible

HTML5 allows us to easily create a collapsible using the `details` and `summary` tags.  
The `details` tag displays its contents based on whether the `summary` has been clicked or not.

``` html
<details class="light">
  <summary class="dark">Buttons</summary>
  <ul class="light">
    <li>List item 1</li>
    <li>List item 2</li>
    <li>List item 3</li>
  </ul>
</details>
```

### Buttons

Success buttons are green and warning buttons are red.

``` html
<!-- Success button -->
<button class="success">Success</button>

<!-- Warning button -->
<button class="warning">Warning</button>
```

### Social Media Logo Links 

The `nav` bar is highly recommended for the first `header`, however is not necessary for anywhere else. There is no support for `footer` media links at the moment.

``` html
<!-- Replace "example123" with your username -->
<!-- Replace "./" with the correct path if necessary -->
<!-- Replace "light" with "dark" if you want black logos -->
<nav>

  <!-- Bitbucket -->
  <a href="https://bitbucket.org/example123">
    <img src="./img/media/Bitbucket.svg" alt="Bitbucket" title="Bitbucket" class="media light">
  </a>

  <!-- CodeSignal -->
  <a href="https://app.codesignal.com/profile/example123">
    <img src="./img/media/CodeSignal.svg" alt="CodeSignal" title="CodeSignal" class="media light">
  </a>

  <!-- DataCamp -->
  <a href="https://www.datacamp.com/profile/example123">
    <img src="./img/media/DataCamp.svg" alt="DataCamp" title="DataCamp" class="media light">
  </a>

  <!-- Dataquest -->
  <a href="https://www.dataquest.io/profile/example123">
    <img src="./img/media/Dataquest.svg" alt="Dataquest" title="Dataquest" class="media light">
  </a>

  <!-- Discord -->
  <a href="https://app.discord.com/profile/example123">
    <img src="./img/media/Discord.svg" alt="Discord" title="Discord" class="media light">
  </a>

  <!-- Facebook -->
  <a href="https://fb.me/example123">
    <img src="./img/media/Facebook.svg" alt="Facebook" title="Facebook" class="media light">
  </a>

  <!-- GitHub -->
  <a href="https://github.com/example123">
    <img src="./img/media/GitHub.svg" alt="GitHub" title="GitHub" class="media light">
  </a>

  <!-- HackerRank -->
  <a href="https://www.hackerrank.com/example123">
    <img src="./img/media/HackerRank.svg" alt="HackerRank" title="HackerRank" class="media light">
  </a>
    
  <!-- Imgur -->
  <a href="https://example123.imgur.com/">
    <img src="./img/media/Imgur.svg" alt="Imgur" title="Imgur" class="media light">
  </a>

  <!-- Instagram -->
  <a href="https://instagram.com/example123">
    <img src="./img/media/Instagram.svg" alt="Instagram" title="Instagram" class="media light">
  </a>

  <!-- Kaggle -->
  <a href="https://www.kaggle.com/example123">
    <img src="./img/media/Kaggle.svg" alt="Kaggle" title="Kaggle" class="media light">
  </a>

  <!-- LeetCode -->
  <a href="https://leetcode.com/example123/">
    <img src="./img/media/LeetCode.svg" alt="LeetCode" title="LeetCode" class="media light">
  </a>

  <!-- LinkedIn -->
  <a href="https://linkedin.com/in/example123">
    <img src="./img/media/LinkedIn.svg" alt="LinkedIn" title="LinkedIn" class="media light">
  </a>

  <!-- Messenger -->
  <a href="https://m.me/example123">
    <img src="./img/media/Messenger.svg" alt="Messenger" title="Messenger" class="media light">
  </a>

  <!-- Pinterest -->
  <a href="https://pinterest.com/example123">
    <img src="./img/media/Pinterest.svg" alt="Pinterest" title="Pinterest" class="media light">
  </a>

  <!-- Reddit -->
  <a href="https://reddit.com/user/example123">
    <img src="./img/media/Reddit.svg" alt="Reddit" title="Reddit" class="media light">
  </a>

  <!-- Slack -->
  <a href="https://example123.slack.com/">
    <img src="./img/media/Slack.svg" alt="Slack" title="Slack" class="media light">
  </a>

  <!-- Snapchat -->
  <a href="https://sc.co/add/example123">
    <img src="./img/media/Snapchat.svg" alt="Snapchat" title="Snapchat" class="media light">
  </a>

  <!-- Telegram -->
  <a href="https://t.me/joinchat/example123">
    <img src="./img/media/Telegram.svg" alt="Telegram" title="Telegram" class="media light">
  </a>

  <!-- Tumblr -->
  <a href="https://example123.tumblr.com/">
    <img src="./img/media/Tumblr.svg" alt="Tumblr" title="Tumblr" class="media light">
  </a>

  <!-- Twitch -->
  <a href="https://www.twitch.tv/example123">
    <img src="./img/media/Twitch.svg" alt="Twitch" title="Twitch" class="media light">
  </a>

  <!-- Twitter -->
  <a href="https://twitter.com/example123">
    <img src="./img/media/Twitter.svg" alt="Twitter" title="Twitter" class="media light">
  </a>

  <!-- WhatsApp -->
  <a href="https://api.whatsapp.com/send?phone=+[insert phone number]&text=[insert message]">
    <img src="./img/media/WhatsApp.svg" alt="WhatsApp" title="WhatsApp" class="media light">
  </a>

  <!-- YouTube -->
  <a href="https://youtube.com/example123">
    <img src="./img/media/YouTube.svg" alt="YouTube" title="YouTube" class="media light">
  </a>

</nav>
```