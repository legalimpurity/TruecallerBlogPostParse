# TruecallerBlogPostParse

Project Specifications
The app should define and run 3 requests SIMULTANEOUSLY, each request is defined below; 1. Truecaller10thCharacterRequest:
• Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web;
• Process the web page content: Find the 10th character in the web page text and report it back to the UI code via a callback. This callback should bring an appropriate data structure.
2. TruecallerEvery10thCharacterRequest:
• Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web;
• Process the web page content: Find every 10th character (i.e: 10th, 20th, 30th, etc.) in the web page text and report it back to the UI code via a callback. This callback should bring an appropriate data structure.
3. TruecallerWordCounterRequest:
• Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web;
• Process the web page content: Split the text into words by using whitespace characters (i.e: space, line feed, etc.) and write a simple algorithm to count the occurrence of every word (case insensitively) in the document and report it back to the UI code via a callback. The callback should bring an appropriate data structure of words and counts, so the UI code can ask how many times the word "truecaller" occurs.
i. You can disregard that there is html/javascript etc in the content. Just treat every "word" equally. For example: <p> Truecaller Hello World </p> should produce +1 for each of these: “<p>”, “Truecaller”, “Hello”, “World”, and “</p>”. No need to distinguish “<p>” than any other word.
In neither of the three requests does the HTML document need to be parsed in any way. Just treat it all like a plain text. There is also no need to make the app parametric, you can define http://www.truecaller.com/support, or 10 (as in 10th character) and such as constants.
The application should show:
• A single button to run the three requests SIMULTANEOUSLY;
• A single textview for each request to be updated as soon as the processing of
corresponding request finishes.
