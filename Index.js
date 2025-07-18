const express = require('express');
const nodemailer = require('nodemailer');

const app = express();
const PORT = process.env.PORT || 3000;

app.get('/', (req, res) => {
  res.send('✅ ZÉNO SMTP Engine is up and running');
});

app.get('/send', async (req, res) => {
  let transporter = nodemailer.createTransport({
    service: 'gmail', // ou autre
    auth: {
      user: 'ton.email@gmail.com',
      pass: 'ton_mot_de_passe_ou_token'
    }
  });

  let mailOptions = {
    from: 'ton.email@gmail.com',
    to: 'terryleck2@live.fr',
    subject: '🔥 Message SMTP depuis ZÉNO',
    text: 'Ceci est un test d’envoi SMTP automatisé via ZÉNO.'
  };

  try {
    let info = await transporter.sendMail(mailOptions);
    res.send('📨 Email envoyé : ' + info.response);
  } catch (err) {
    console.error(err);
    res.status(500).send('Erreur SMTP : ' + err.message);
  }
});

app.listen(PORT, () => {
  console.log(`✅ SMTP Engine démarré sur le port ${PORT}`);
});
