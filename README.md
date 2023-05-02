`ctrl + alt right` is for parens (slurping)

`ctrl + alt c, ctrl + alt j` select deps.edn. jacking in. Starts repl and connect editor to it. Once jacked in you can do `alt + enter` inside a statement (form) to execute it. `ctrl + enter` evaluates nested things.

`ctrl + alt c, enter` evaluates the file you're in. After making changes

## cli

run via babashka (prefered)

```
clj -M:cli dit
```

`-M` stands for main and maps to namespace `:cli` defined in deps.edn

### alt way

run via deps edn

```
clj -X:run :name '"dit"'
```
