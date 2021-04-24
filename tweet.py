import tweepy as tw
from os.path import exists
import json
from sys import argv
import nltk
nltk.download("stopwords")
from nltk.corpus import stopwords

SECRET = "8oL7JYEgzi6wOzDCb5mRd0tbtCYX3twTbK9QxPFbl7wSOAP4du"
key = "HXGI4hx67yucnmBnXCOj5739W"

auth = tw.OAuthHandler(key,SECRET)

api = tw.API(auth)
search_words = "#covid -RT" # SEARCH WORD
date_since = "2021-04-23" # EXTRACT TWEETS SINCE WHEN

def filter_newline(string):
    # REMOVES NEW LINE FROM SENTENCES
    if "\n" in string:
        if string.index("\n") == len(string)-1:
            return string[:-1]
        else:
            return string[:string.index("\n")] + string[string.index("\n")+1:]
    else:
        return string

def generate_tweets(search_words,qty):
    # THIS PART ACTUALLY FETCHES THE TWEETS FROM TWITTER
    return tw.Cursor(api.search,
              q=search_words,
              lang="en",
              since=date_since).items(qty)

def extract_raw_data(tweets):
    # JUST TAKES TWITTER TWEETS AND GENERATE DICT
    return {"data":[each._json for each in tweets]}

def extract_tweets(tweets):
    # Iterate and print tweets
    database = {"data":[]} #[]variables #creation of an object "database".
    for tweet in tweets: #each tweet from the list of tweets
        #print(tweet.text)
        # HERE YOU CAN DECIDE WHAT INFO YOU WANT FROM EACH TWEET
        # i SAVED USER_MENTIONS AND TEXT, YOU CAN EXTRACT ACCOUNT DETAILS AND MORE FROM tweet.
        database["data"].append({"text":tweet.text,"mentions":tweet.entities["user_mentions"]}) # we extract text and mentions from each tweet
    return database 

def save_json(database,filename='programming.json'): #
    #SAVES JSON TO A FILE
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
        json.dump(prevData,mt)

def calc_frequency(database):
    # CALCULATES FREQUENCY OF A WORD
    mentions = {}
    for each in database["data"]:
        for eachMention in each["mentions"]:
            organization = eachMention["screen_name"]
            if organization in mentions:
                mentions[organization] += 1 #if the organisation exists already we add "1" to the number of occurence.
            else:
                mentions.update({organization:1}) #if the organisation does not exist, we create the organisation and add 1.
            #mentions.update({eachMention["screen_name"] : mentions[eachMent])
        #mentions.extend(each["mentions"])
    return mentions #we return mentions in total

def check_word_exists(words,search): #is, Is, iS, we
    # CHECK IF A WORD EXTSTS IN A LIST OF WORDS
    for each in words:
        if each.lower() == search.lower(): #first compares and converts
            return True
    return False

def process_sentences(string):
    # NLP STUFF TO PROCESS SENTENCE
    stop_words = set(stopwords.words('english'))
    data = string.split()
    result = []
    for word in data:
        if not check_word_exists(stop_words,word) and len(word)>0 and "http" not in word and word.isalpha() and word!="RT" and word!="\n":
            result.append(word)
    return result

def data_process(database):
    # TAKES A LOT OF TWITTER DATA AND CLEAN IT
    # REMOVES EMOJI AND STUFF
    texts = {}
    stop_words = set(stopwords.words('english'))

    for each in database["data"]:
        eachText = each["text"]
        eachText = eachText.split() # get each word list
        for word in eachText:
            if not check_word_exists(stop_words,word) and len(word)>0 and "http" not in word and word.isalpha() and word!="RT" and word!="\n":
                word = filter_newline(word)
                if word not in texts: #if the word appears for the first time, we add 1 (#77)
                    texts.update({word:1})
                else:
                    texts[word]+=1
    return texts
    # filteredWord a list of all non stop words
        
def frequency_of_word(database,n):
    # AGAIN CHECKS FREQUENCY
    frequencies = list(database.values()) # all frequency 21,34
    frequencies.sort(reverse=True)
    #frequency.sort() -> ascending
    topFrequencies = frequencies[:n] #first n values
    words = {}
    for each in topFrequencies:
        for word in database:
            if database[word] == each:
                words.update({word:each})
    return words

def extract_readable_text(string):
    # RETURNS A READABLE FORMAT TO BE PRINTED ON COMMAND LINE
    output = ""
    for each in string:
        if each.isalpha() or each==" " or each=="\n":
            if each=="\n":
                each = " "
            output+=each
    return output

def find_prediction(database,value):
    value = value.lower()
    for each in database["data"]:
        lowerVersion = each["text"].lower()
        lowerVersion = filter_newline(lowerVersion)
        if value in lowerVersion:
            if lowerVersion.find(".") > lowerVersion.index(value) and lowerVersion.find(".") == len(lowerVersion):
                yield extract_readable_text( lowerVersion[lowerVersion.index(value):lowerVersion.find(".")])
            else:
                yield extract_readable_text( lowerVersion[lowerVersion.index(value):])

def get_file_data(filename='programming.json'):
    # READS JSON DATA FROM A FILE
    with open(filename,"r",encoding="UTF-8") as f: #r= read only. f =  file.
        return json.load(f) #using the json library (get the file in a )

if __name__ == "__main__":
    load = False
    fileName = "poorna6.json"
    if len(argv)==2:
        load = True
        fileName = argv[1].strip()
    tweets = {}
    formattedData = {}
    if load:
        tweets = get_file_data(fileName)
        formattedData = tweets
    else:
        # THIS IS WHAT REALLY MATTERS 
        # RAGHAV SEE HERE , KEYWORD IS WORD YOU ARE LOOKING FOR IN TWEETS
        # 
        keyword = input("Enter The Searcword : ")
        tweets = generate_tweets(keyword,100)
        formattedData = extract_tweets(tweets)
        print(formattedData)
        save_json(formattedData,fileName)
    #rawData = extract_raw_data(tweets)
    #print(formattedData,rawData)
    # newData = api.search("life",result_set="recent",count="100")
    # newData = extract_tweets(newData)
    
    # for each in extract_tweets(newData)["data"]:
    #     print(each["text"])
    
    calculatedFrequency = calc_frequency(formattedData)
    # print(calculatedFrequency)
    processedData = data_process(formattedData)
    
    #print(processedData)
    wordFrequency = frequency_of_word(processedData,10)
    print("Words\n",wordFrequency)
    print("Companies\n",frequency_of_word(calculatedFrequency,10))
    # for each in formattedData["data"]:
    #     each = each["text"]
    #     lowerVersion = each.lower()
    #     if "job" in lowerVersion and "negotiation" in lowerVersion:
    #         print(each)
    print("Completion Suggestions")
    # THEN YOU CAN CHECK FOR A PARTICULAR WORD THAT SHOULD BE MENTIONED IN THE TWEET
    # EXAMPLE IF YOU ENTER OXYGEN THEN ALL TTWEETS WITH OXYGEN IN IT WILL COME
    print(list(find_prediction(formattedData,input("Enter a sentence to complete : "))))
    #print(list(find_prediction(newData,"life is")))