module.exports = {
  title: 'Meteor',
  tagline: 'Meteor Documentation',
  // Set the production url of your site here
   url: 'https://github.com',
   // Set the /<baseUrl>/ pathname under which your site is served
   // For GitHub pages deployment, it is often '/<projectName>/'
   baseUrl: '/meteor/',

   // GitHub pages deployment config.
   // If you aren't using GitHub pages, you don't need these.
   organizationName: 'getspherelabs', // Usually your GitHub org/user name.
   projectName: 'meteor', // Usually your repo name.
   deploymentBranch: 'gh-pages',
   onBrokenLinks: 'throw',
   onBrokenMarkdownLinks: 'warn',
  themeConfig: {
    navbar: {
      title: 'docs',
      logo: {
        alt: 'LiveKit Logo',
        src: 'img/logo.svg',
      },
      items: [
        {
          href: 'https://getpspherelabs.github.io/meteor/',
          label: 'Home',
          position: 'right',
        },
        {
          href: '/',
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
      copyright: `Copyright © ${new Date().getFullYear()} Meteor`,
    },
    colorMode: {
      respectPrefersColorScheme: false,
      defaultMode: 'night',
      disableSwitch: true,
    },
    prism: {
      theme: require('./themes/meteor'),
      additionalLanguages: ["swift", "kotlin"],
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
          {
            to: '/deploy',
            from: ['/guides/deploy/prepare', '/guides/deploy'],
          },
          {
            to: '/deploy/vm',
            from: ['/guides/deploy/instance'],
          },
          {
            to: '/deploy/kubernetes',
            from: ['/guides/deploy/kubernetes'],
          },
          {
            to: '/deploy/test-monitor',
            from: ['/guides/deploy/tuning'],
          },
          {
            to: '/deploy/benchmark',
            from: ['/guides/deploy/benchmark'],
          },
          // {
          //   to: '/deploy/recorder',
          //   from: ['/guides/deploy/recorder'],
          // },
        ],
      }
    ],
  ]
};
