# LiqPaySamplePrj
LiqPay sample project. In case of bad LiqPay documentation I created this test project to create sample payment with LiqPay service

How to get this project running:
1. Get registered on LiqPay.ua
2. Create your company in personal area on site
3. Create API keys for company
4. Update constant values in ConfigurationService class:
   a) LiqPayPublicKey - public API key of your company on LiqPay service
   b) YOUR_LIQPAY_PRIVATE_KEY - appropriate private key
5. That's all :) 

Some things to know.
LiqPay requires some android permissions. That permissions are automatically requested on first app run
