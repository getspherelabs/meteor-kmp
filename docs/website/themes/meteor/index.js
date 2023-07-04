var theme = {
  plain: {
    background: "#00153C",
    color: "#ffffff",
  },

  styles: [
    {
      types: ["comment", "block-comment", "prolog", "doctype", "cdata"],
      style: {
        color: "#93a1a1",
      },
    },
    {
      types: ["punctuation"],
      style: {
        color: "#999999",
      },
    },
    {
      types: [
        "namespace",
        "unit",
        "hexcode",
      ],
      style: {
        opacity: "0.7",
      },
    },
    {
      types: ["atrule", "attr-value", "keyword"],
      style: {
        color: "#d340d0",
      },
    },
    {
      types: ["function"],
      style: {
        color: "#85EFE3",
      },
    },
    { 
      types: ["method"],
      style: {
        color: "#8DF253",
      },
    },
    {
      types: ["regex", "important", "variable"],
      style: {
        color: "#ee9900",
      },
    },
    {
      types: ["important", "bold"],
      style: {
        fontWeight: "bold",
      },
    },
    {
      types: ["italic"],
      style: {
        fontStyle: "italic",
      },
    },
    { 
      types: ["entity"],
      style: {
        cursor: "help",
      }
    },
    {
      types: ["tag", "boolean", "number", "constant", "symbol", "deleted"],
      style: {
        color: "#9F83FF",
      },
    },
    {
      types: ["selector", "attr-name", "string", "char", "builtin", "inserted"],
      style: {
        color: "#5A8BFF",
      },
    },
    {
      types: ["operator", "entity", "url"],
      style: {
        color: "#a67f59",
        background: "transparent",
      },
    },
  ],
};

export default theme;