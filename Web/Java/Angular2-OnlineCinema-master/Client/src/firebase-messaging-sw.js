importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-messaging.js');
firebase.initializeApp({
  'messagingSenderId': '278743644132'
});
const messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function () {
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png'
  };
  return self.registration.showNotification(notificationTitle, notificationOptions);
});
