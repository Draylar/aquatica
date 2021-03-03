colors = [ "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" ]
wood_types = [ "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" ]

for color in colors:
    for wood in wood_types:
        file_name = f"{color}_{wood}_chair"
        file_name_ext = f"{file_name}.json"

        with open(file_name_ext, 'w') as file:
            file.write(f"""{{
  "variants": {{
    "": {{
      "model": "aquatica:block/chair/{file_name}"
    }}
  }}
}}
                """
            )
            file.close()