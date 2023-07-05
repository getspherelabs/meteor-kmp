module.exports = {
  title: 'Meteor',
  tagline: 'Meteor Documentation',
   url: 'https://github.com',
   baseUrl: '/meteor/',

   organizationName: 'getspherelabs', // Usually your GitHub org/user name.
   projectName: 'meteor', // Usually your repo name.
   deploymentBranch: 'gh-pages',
   onBrokenLinks: 'throw',
   onBrokenMarkdownLinks: 'warn',
   themeConfig: {
    navbar: {
      title: 'Meteor Multiplatform',
      items: [
        {
          href: 'https://getspherelabs.github.io/meteor/',
          label: 'Home',
          position: 'right',
        },
        {
          href: 'https://github.com/getspherelabs/meteor',
          label: 'GitHub',
          position: 'right',
          className: 'github',
        },
      ],
    },
    footer: {
      style: 'light',
      links: [
      ],
      copyright: `Copyright © ${new Date().getFullYear()} Meteor Multiplatform`,
    },
    colorMode: {
      respectPrefersColorScheme: false,
      defaultMode: 'light',
      disableSwitch: true,
    },
    prism: {
      theme: require('./themes/meteor'),
      additionalLanguages: ["swift", "kotlin","groovy"],
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          routeBasePath: '/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
        sitemap: {
          changefreq: 'weekly',
          priority: 0.5,
        },
      },
    ],
  ],
  plugins: [
    [
      '@docusaurus/plugin-client-redirects',
      {
        redirects: [
          // {
          // },
        ],
      }
    ],
  ]
};
