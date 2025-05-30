<!-- src/components/layout/Footer.vue -->
<template>
  <footer class="site-footer">
    <div class="footer-inner">
      <div class="company">㈜아이티스마트</div>
      <div class="info">
        서울특별시 강서구 양천로 583, A동 2010호 (염창동, 우림블루나인 비즈니스센터) / Tel :
        02-830-8700 / Fax : 02-830-0550 / Email : admin@itsmart.co.kr /
        <a href="#">개인정보처리방침</a>
      </div>
      <div class="copyright">COPYRIGHT(c)ITSMART.CO.LTD.,ALL RIGHTS RESERVED</div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer',
}
</script>

<style scoped>
.site-footer {
  background-color: #212529;
  color: white;
  padding: 1rem;
  font-size: 0.85rem;
}

.footer-inner {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
  line-height: 1.5;
}
a {
  color: #fff;
  text-decoration: underline;
}
</style>
