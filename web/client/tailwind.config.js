/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        customLightGreen: '#1DB954',
        customDarkGreen: '#3D5300',
        customLightYellow: '#FFE31A'
      },
    },
  },
  plugins: [],
}

