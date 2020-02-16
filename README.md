# Flext
Flext is an Android IM platform that analyzes emotions involved in a text message through IBM Watson services and shows it to the recipient. 

## Background

As mobile messaging applications have ascended to become one of the most widely used channels of communication in the recent past, the overall efficiency and effectiveness of these services have improved exponentially. Despite these advances, we believe that there is room for an all-encompassing instant messaging system which addresses several issues associated with these types of applications, while leaving space for the addition of new useful and entertaining features – which we also hope to demonstrate.

In particular, we believe that those in some parts of the world remain averse to these applications due to language barriers. Many popular market dominant messaging platforms such as WhatsApp and Facebook Messenger do not offer UIs in other languages, whereas a majority of other applications such as search engines and operating systems have already been localized. This results in those who are not well versed in the English language, such as those living in poverty with poor education, being unable to operate these applications, even as smartphones become more affordable solutions in these markets. One of our primary goals is to provide these users to a messaging platform where the UI is in a language familiar to them. 

Also we have identified that unlike other communication channels we use like telephone or video calling, instant messaging lack the emotional transaction between two parties. Even though many platforms try to overcome this by introducing emojis and stickers, still older generations doesn't have a tendency to use them. At the end, while instant messaging is quick and efficient, we have felt that it lack the emotional understanding between messages as such tones can impact the effectiveness of communication in different contexts.

Security within point to point communication applications has been called into concern recently. While the point to point encryption which has become the standard for messaging apps over the years provides a very strong layer of encryption, their transparency and levels of customizability leave something to be desired. WhatsApp for example uses public and private keys between users, uses a single public key for each user. 

## Scope

One of the main selling points of our application is to be its highly customizable UIs that support any Unicode character. This will allow us to create localized UIs that those who are unfamiliar or uncomfortable with the English language would able be operate with ease. It is our goal to prepare a Sinhala UI option at the end of our project, demonstrating its capabilities to other developers who would be interested in adopting it to a different languages. Compared to other messaging applications such as WhatsApp and WeChat, which limit themselves to one language, our application will allow users of different linguistic backgrounds to communicate with each other through interfaces that have been localized for them.

We also aim to offer more secure messaging services using encryption systems and that uses multiple keys. This will be done via means of an optional encryption service that, on top of standard encryption, allows users to set up custom encryption between two specific devices, such that to any other entity, the contents of the messages are completely indecipherable. We believe this will provide a sufficient service to those in need of truly secure communications.

As a demonstration of the modularity and the capabilities of such an application, we plan to implement a sentiment analysis feature that users enable while chatting. This feature will make use of deep learning neural networks to analyse the messages sent back and forth and detect emotions (anger, disgust, fear, sadness and joy), social propensities (openness, conscientiousness, extroversion, agreeableness and emotional range) and language styles (analytical, confident and tentative). This can then be displayed on the receiver’s screen.

We only focus on building the android application. Therefore iOS, Windows Mobile and Web application are out of scope. 

Functional Requirements

- System must be able to register user.

- System shall be able to login a user.

- System must allow the user to logout. 

- System must allow user to reset the password with an email.

- System shall allow user to send text messages to other users.

- System shall allow users to receive text messages from sender.

- System must allow the user to see number of received text messages.

- System must must show the online users.

- System must show the sender if the recipient has seen the text message.

- System must allow users to upload a profile picture.

- System must notify the recipient with a notification.

- System must support both landscape and portrait mode.

- System must allow the user to search for other users.

- System must display the chat threads.

- System will allow the user to send emojis.

- System must show the recipient with his/her profile picture.

- System shall not ask the user to re-login once the user is logged in.

- System shall be able to analyze recipient text message and provide sentimental analysis on it.

Non-functional Requirements

- System must be vertically scalable to add new features.

- System must be horizontally scalable, i.e. expandable in order to accommodate new users.

- System must be maintainable.

- Password reset emails should be sent in less than 5 minutes of latency.

- System must be secure with the latest security protocols.

- System must be reliable with no more than 30 seconds of down time.

- Application must be stable to run on a single-core mobile processor.

## Tools and Technical Requirements

- Android Studio - (General IDE for the entire project.)

   Android SDK 29 - (Primary development kit used to develop the application.)
  
   Google Pixel 2 Virtual Device - (Simulator for testing and debugging the application.)
  
- Programming language - Java

  Java SE Development Kit 12.0 - (Native language for developing the language)
  
- Third-party Services

  Google Firebase
  
  Firebase-auth 16.0.5 - (For authentication of user accounts)
  
  Firebase-database 16.0.4 - (Real time DB for the application)
  
  Firebase-core 16.0.4
  
  Firebase-storage 16.0.4
  
  Firebase-storage 16.0.4 - (To store user profile pictures)
  
- IBM Cloud

  IBM Watson Tone Analyzer - (used to provide sentimental analysis - emotional analysis)

## Snapshots 

![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Menu_Page.JPEG)
![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Login.JPEG)
![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Register.JPEG)
![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Password_Reset.JPEG)
![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Chats.JPEG)
![](https://github.com/thisaldesilva/Flext/blob/master/Flext/Users.JPEG)
