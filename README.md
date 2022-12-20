# Setup Project
## Dependancies
- minimum required node version: v12.16.3
## Install Prject Dependancies
- Run command `npm ci`

## Run MailCatcher
- Steps to install mailcatcher : 
- command for running mailcatcher: `mailcatcher --ip 0.0.0.0 --smtp-port 9001 --foreground`

## Environment Variables
- If you want to run this sample app with actual mail sending instance you can rename .env.prod.sample file to .env.prod and fill in required Details
# Running App
- For local version simply run command `npm run local`
- For prod version first populate environment variables in .en.prod as mentioned above and run command `npm run prod`