import tweepy
import pandas as pd
from flask import Flask,render_template

app = Flask(__name__)

SECRET = "eU6TNCjSAhtSD1MW00tZgMh2jJTOasbP5HBavrDymUkthqy6BP"
key = "yVoN3zvgrnNu9Ihl6eJlzC0Dd"
keys="746612452196921344-kTNcG7FhAZSFyAoKXskNJUVOjQzeK5O"
Access_secret="gjjtBCuyZ8tgzSkGZnvSY4ASXWl3c9thl9lRjywqNlK5j"

auth = tweepy.OAuthHandler(key,SECRET)
auth.set_access_token(keys,Access_secret)
api = tweepy.API(auth)


# Define the search term and the date_since date as variables
search_words = "#Oxygen"
date_since = "2021-04-24"

@app.route('/')
def home():
    return render_template("index.html")


@app.route('/fetch_oxy')
def fetch_oxy():
    tweets = tweepy.Cursor(api.search,
              q=search_words,
              lang="en",
              since=date_since).items(15)
    data = []
    for tweet in tweets:
        data.append(tweet.text)

    final = data
    # return final[0]
    return render_template("oxy.html",final = final)

@app.route('/fetch_vaccine')
def fetch_vaccine():
    tweets = tweepy.Cursor(api.search,
              q="Covid Vaccine",
              lang="en",
              since=date_since).items(15)
    data = []
    for tweet in tweets:
        data.append(tweet.text)

    final = data
    # return final[0]
    return render_template("vacc.html",final = final)

@app.route('/fetch_beds')
def fetch_beds():
    tweets = tweepy.Cursor(api.search,
              q="hospital beds",
              lang="en",
              since=date_since).items(15)
    data = []
    for tweet in tweets:
        data.append(tweet.text)

    final = data
    # return final[0]
    return render_template("beds.html",final = final)

# @app.route('/fetch_pm')
# def f
# etch_pm():
#     number_of_tweets = 100
#     tweets = []
#     likes = []
#     time = []

#     for i in tweepy.Cursor(api.user_timeline,id='narendramodi',tweet_mode="extended").items(number_of_tweets):
#         tweets.append(i.full_text)
#         likes.append(i.favorite_count)
#         time.append(i.created_at)
#         df = pd.DataFrame({'tweets':tweets,'likes':likes,'time':time})

#     return render_template("pm.html", df = df)

# @app.route('/fetch_bedsBangalore')
# def fetch_oxy():
#     tweets = tweepy.Cursor(api.search,
#               q=search_words,
#               lang="en",
#               since=date_since).items(15)
#     data = []
#     for tweet in tweets:
#         data.append(tweet.text)

#     final = data
#     # return final[0]
#     return render_template("oxy.html",final = final)



@app.route('/dummy')
def dummy():
    return render_template("choices.html")


if __name__ == "__main__":
    app.run(debug=True)