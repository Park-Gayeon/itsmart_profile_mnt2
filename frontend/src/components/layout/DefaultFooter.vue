<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <p>&copy; {{ currentYear }} {{ companyName }}. All rights reserved.</p>
      </div>
      <div class="footer-section">
        <div class="footer-links">
          <a href="#" @click.prevent="$emit('navigate', 'privacy')">개인정보처리방침</a>
          <a href="#" @click.prevent="$emit('navigate', 'terms')">이용약관</a>
          <a href="#" @click.prevent="$emit('navigate', 'contact')">문의하기</a>
        </div>
      </div>
      <div class="footer-section">
        <div class="version-info">
          <span>Version {{ version }}</span>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'DefaultFooter',
  props: {
    companyName: {
      type: String,
      default: 'My Company',
    },
    version: {
      type: String,
      default: '1.0.0',
    },
  },
  emits: ['navigate'],
  computed: {
    currentYear() {
      return new Date().getFullYear()
    },
  },
}
</script>

<style scoped>
.footer {
  background-color: #2c3e50;
  color: #ecf0f1;
  padding: 2rem;
  margin-top: auto;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 1rem;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section {
  display: flex;
  align-items: center;
}

.footer-links {
  display: flex;
  gap: 1.5rem;
}

.footer-links a {
  color: #bdc3c7;
  text-decoration: none;
  font-size: 0.9rem;
  transition: color 0.3s ease;
}

.footer-links a:hover {
  color: #3498db;
}

.version-info {
  font-size: 0.8rem;
  color: #95a5a6;
}

.footer-section p {
  margin: 0;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    text-align: center;
    gap: 1.5rem;
  }

  .footer-links {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>
