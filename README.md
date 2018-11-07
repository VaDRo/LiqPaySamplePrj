# LiqPaySamplePrj
LiqPay sample project. In case of bad LiqPay documentation I created this test project to create sample payment with LiqPay service

How to get this project running:
1. Register on LiqPay.ua
2. Create your company
3. Create API keys
4. Update constants values in ConfigurationService class:
   a) LiqPayPublicKey - public API key of your company on LiqPay service
   b) YOUR_LIQPAY_PRIVATE_KEY - appropriate private key
5. That's all :) 

Some things to know.
LiqPay requires some android permissions. That permissions are automatically requested on first app run
