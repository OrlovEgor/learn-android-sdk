curl -X POST -H "Authorization: key=AAAAhSG5Fzc:APA91bGDs1Gb-4NZlcEkwwB4_UbW-8BiPc7q1Cke3KvMDTidmDL43DoeLCPfEShExsQTsICqOxdPeGXm_Eo5fDbPVO3qjc0dpP8YqIHzllFFa-fUT4B-tiFLQYkfKMSotd3V9wNaV5Sy" -H "Content-Type: application/json" -d '{
    "to":"cnecR-FtT6aVci3vINyYf8:APA91bF4ax3HFg3xZqtgAfB2l2GvukLapMcCfotj2uGhFG6dFXZtqfGnzMypRFxf3kaDIrkpNC6cYPysG4fKsrd_NaVaON7_6AB1GQ0HWZBJCXiuU8SoUAEWyT0_HE566ZBk--GngJ4c",
    "notification": {
      "user": "Ivan Ivanov",
      "message": "Hi"
    }
}' https://fcm.googleapis.com/fcm/send -v -i