Resources:
- https://stackoverflow.com/questions/17766541/saving-and-reusing-the-result-of-a-sparql-queryInfo

- ResultSetFormatter: https://stackoverflow.com/questions/16624777/jena-result-set-to-string
- rs. options (https://stackoverflow.com/questions/67338896/save-a-resultset-to-an-array-in-java)

To do:
1. Handle sparql queries:

- If queryInfo is incorrect and doesn't return result it shouldn't be saved (message)

- proveri za COUNT

- istestiraj za sekakvi tipovi queryInfo

- pogledni controlling prefixes ushte ednash

- QueryParseException: Encountered "<EOF>" at line 1, column 42.

2. Fix JWT auth 

- Only one person can log in with a certain mail
- Check if "invalid credentials error" is due to same address
- Work on the tokens on frontend

4. error handling & refactor

- session timed out

6. AUTH - fix JWT length
7. Fix added endpoints
11. povlechi endpoints ako mozhes

