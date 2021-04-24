import json

data = open("covidnew.json")
d = json.load(data)

new = d['data']

for i in new:
    a = i['text']
    b = []
    b.append(a)
    for i in b:
        print(i)
    print(b)

# print(new)