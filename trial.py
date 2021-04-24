import json

f = open('poorna.json')


data = json.load(f)

datas = data['data']

# print(datas[0])
# print(datas[1])
# print(datas[3])

final = datas[1]
print(final['text'])