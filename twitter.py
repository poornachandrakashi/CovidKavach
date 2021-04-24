from tweepy import *

import pandas as pd
import csv
import re 
import string
import preprocessor as p

consumer_secret = "8oL7JYEgzi6wOzDCb5mRd0tbtCYX3twTbK9QxPFbl7wSOAP4du"
consumer_key = "HXGI4hx67yucnmBnXCOj5739W"
access_key= "746612452196921344-OQbdous6VRcvhE2ZwKdNjG2AhnDDYfe"
access_secret = "bZhMlh4TagW67PjweOkCJjtkcJd0GeMgZSNUZUXfenofZ"


auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_key, access_secret)


api = tweepy.API(auth,wait_on_rate_limit=True)



csvFile = open('file-name', 'a')
csvWriter = csv.writer(csvFile)

search_words = "covid19"      # enter your words
new_search = search_words + " -filter:retweets"

for tweet in tweepy.Cursor(api.search,q=new_search,count=100,
                           lang="en",
                           since_id=0).items():
    csvWriter.writerow([tweet.created_at, tweet.text.encode('utf-8'),tweet.user.screen_name.encode('utf-8'), tweet.user.location.encode('utf-8')])

