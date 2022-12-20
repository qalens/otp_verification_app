require('dotenv').config({ path: `.env.${process.env.NODE_ENV}` })
const express=require('express');
const bodyparser=require('body-parser');
const nodemailer=require('nodemailer');
const path=require('path');
const { engine } = require('express-handlebars');

const app=express();

// view engine setup
app.engine('handlebars', engine({ extname: '.handlebars', defaultLayout: false}));
app.set('view engine','handlebars');

// body parser middleware
app.use(bodyparser.urlencoded({extended : false}));
app.use(bodyparser.json());

//static folder
app.use('/public',express.static(path.join(__dirname, 'public')));


app.get('/',function(req,res){
    res.render('contact');
});

var email;

var otp

function getNewOtp(){
    otp = Math.random();
    otp = otp * 1000000;
    otp = parseInt(otp);
    return otp
}

let transporter = nodemailer.createTransport(JSON.parse(process.env.SMTP_DETAILS));
    
app.post('/send',function(req,res){
    email=req.body.email;

     // send mail with defined transport object
    var mailOptions={
        from: process.env.FROM,
        to: req.body.email,
        subject: "Otp for registration is",
        html: "<h3>OTP for account verification is </h3>"  + "<h1 style='font-weight:bold;'>" + getNewOtp() +"</h1>" // html body
     };
     
     transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            return console.log(error);
        }
        console.log('Message sent: %s', info.messageId);   
        console.log('Preview URL: %s', nodemailer.getTestMessageUrl(info));
  
        res.render('otp');
    });
});

app.post('/verify',function(req,res){

    if(req.body.otp==otp){
        res.render('success');
    }
    else{
        res.render('otp',{msg : 'otp is incorrect'});
    }
});  

app.post('/resend',function(req,res){
    var mailOptions={
        from: process.env.FROM,
        to: email,
        subject: "Otp for registration is",
        html: "<h3>OTP for account verification is </h3>"  + "<h1 style='font-weight:bold;'>" + otp +"</h1>"
     };
     
     transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            return console.log(error);
        }
        console.log('Message sent: %s', info.messageId);   
        console.log('Preview URL: %s', nodemailer.getTestMessageUrl(info));
        res.render('otp',{msg:"otp has been sent"});
    });

});

const PORT=process.env.PORT||3000;
app.listen(PORT,()=>{
    console.log(`app is live at ${PORT}`);
})