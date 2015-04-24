#import nltk
#nltk.download()
from nltk.corpus import cmudict
from collections import defaultdict

vowels = ['AA','AE','AH','AO','AW','AY',
          'EH','ER','EY',
          'IH','IY',
          'OW','OY',
          'UH','UW']


def last_syllable(word):
    syllable = []
    for p in word[::-1]:
        syllable.append(p)
        stripped_p = p.translate(dict((ord(d), None) for d in ['0','1','2']))
        if stripped_p in vowels:
            break
    return syllable[::-1]

#d = defaultdict(lambda: [''], cmudict.dict())
d = cmudict.dict()

cat = open('cat-in-the-hat.txt').read().lower()

chars_to_remove = ['.', '?', '!', ',', '\'']
chars_dict = dict((ord(key), None) for key in chars_to_remove)
chars_dict[ord('-')] = ' ' # fix 'up-up-up'

cat = cat.translate(chars_dict)
cat_lines = cat.split('\n')

# defaults to the first pronunciation out of all possible ones
p_lines = [[d[w][0] for w in line.split()] for line in cat_lines]

stanzas = []
stanza = []
for line in p_lines:
    if line == []:
        stanzas.append(stanza)
        stanza = []
    else:
        stanza.append(line)

last_words_stanzas = []
for stanza in stanzas:
    last_words = []
    for line in stanza:
        last_words.append(line[-1])
    last_words_stanzas.append(last_words)

last_syllables_stanzas = []
for last_words_stanza in last_words_stanzas:
    last_syllables = []
    for last_word in last_words_stanza:
        last_syllables.append(last_syllable(last_word))
    last_syllables_stanzas.append(last_syllables)

for i,lss in enumerate(last_syllables_stanzas):
    if lss[1] == lss[3]:
        print("rhyme:", last_words_stanzas[i][1], last_words_stanzas[i][3])


print('done')
