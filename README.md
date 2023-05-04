# Kinv (ALPHA)
Bukkit UI library for Kotlin

```kt
val kinv = Kinv.build {
  title = "Hello, Kinv!"
  type = KinvType.Type9x5
  
  body {
    fixed( selector().border(row = 5), ItemStack(Material.BLACK_STAINED_GLASS_PANE) )
    
    button( selector(3 to 5), ItemStack(Material.DIAMOND) ) {
      it.player.sendMessage("You clicked DIAMOND!");
    }
  }
}
```
