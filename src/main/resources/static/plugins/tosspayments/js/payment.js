// SEE: https://developers.tosspayments.com/304121/accounts/411128/phases/test/payment-logs
window.addEventListener('DOMContentLoaded', () => {

  const clientKey = 'test_ck_k6bJXmgo28e1RagkzMe8LAnGKWx4';
  // const clientOrigin = 'http://www.marketgg.shop';
  const clientOrigin = 'http://localhost:5050';
  const tossPayments = TossPayments(clientKey);

  tossPayments.requestPayment('카드', {
    amount: document.getElementById('amount').textContent,
    orderId: document.getElementById('orderId').textContent,
    orderName: document.getElementById('orderName').textContent,
    successUrl: `${clientOrigin}/payments/success`,
    failUrl: `${clientOrigin}/payments/fail`,
    windowTarget: "iframe",
    customerName: document.getElementById('customerName').textContent,
    customerEmail: document.getElementById('customerEmail').textContent,
  });
});
