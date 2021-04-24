#Lets start the HACKBMU project
from flask import Flask
import tweepy as tw
from os.path import exists
import json
from sys import argv
import nltk
nltk.download("stopwords")
from nltk.corpus import stopwords
import json

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
              since="2021-04-15").items(10)
    # Iterate and print tweets
    database = {"data":[]} #[]variables #creation of an object "database".
    for tweet in data: #each tweet from the list of tweets
        #print(tweet.text)
        # HERE YOU CAN DECIDE WHAT INFO YOU WANT FROM EACH TWEET
        # i SAVED USER_MENTIONS AND TEXT, YOU CAN EXTRACT ACCOUNT DETAILS AND MORE FROM tweet.
        database["data"].append({"text":tweet.text,"mentions":tweet.entities["user_mentions"]}) # we extract text and mentions from each tweet
    
    #Saving data in Json file
    filename = "covidnew.json"
    prevData = {}
    if exists(filename):
        with open(filename,"r",encoding="UTF-8") as f:
            prevData = json.load(f)
            for each in database["data"]:
                check = True
                for i in prevData["data"]:
                    if i["text"].lower() == each["text"].lower():
                        check = False
                        break
                if check:
                    prevData["data"].append(each)
    else:
        prevData = database
    with open(filename,'w',encoding="UTF-8") as mt:
        #mt.write(json.dumps(tweet)+'\n')
        return json.dump(prevData,mt)
        

    # return json.dump(prevData,mt)
    return "Success"


@app.route('/display')
def display():
    final = open('covidnew.json')
    data = json.load(final)

    datas = data['data']
    for i in range(10):
        new = datas[i]
    #     data_final = datas[i]
        news =[]
        news.append(new)

    return str(news)

    # return "error"

    

if __name__=="__main__":
    app.run(debug=True)

