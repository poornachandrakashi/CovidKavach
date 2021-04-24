#Lets start the HACKBMU project
from flask import Flask
import tweepy as tw
from os.path import exists
import json
from sys import argv
import nltk
nltk.download("stopwords")
from nltk.corpus import stopwords


app = Flask(__name__)

SECRET = "JDMres23xSHLCvuZK06SfFBMcqk7xVjI9oW7vE9ifAd1gSZT9L"
key = "Z9nvZItasSJaf49Ijm4um2eIu"

auth = tw.OAuthHandler(key,SECRET)

api = tw.API(auth)

@app.route("/")
def index():
    return "Hello World!!!"

@app.route('/fetch_tweets')
def fetch():
    data = tw.Cursor(api.search,
              q="oxygen",
              lang="en",
              since="2021-04-15").items(100)
    # Iterate and print tweets
    database = {"data":[]} #[]variables #creation of an object "database".
    for tweet in data: #each tweet from the list of tweets
        #print(tweet.text)
        # HERE YOU CAN DECIDE WHAT INFO YOU WANT FROM EACH TWEET
        # i SAVED USER_MENTIONS AND TEXT, YOU CAN EXTRACT ACCOUNT DETAILS AND MORE FROM tweet.
        database["data"].append({"text":tweet.text,"mentions":tweet.entities["user_mentions"]}) # we extract text and mentions from each tweet
    return database

    
if __name__=="__main__":
    app.run(debug=True)

