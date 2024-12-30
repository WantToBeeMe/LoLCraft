import os
import json

def generate_base_and_number_files():
    """
    Generates base files for 'number_cooldown_ma' and 'number_cooldown_am',
    and then generates number files (0-9) for each of these base files.
    """

    # Base file names and their translation values
    base_files = {
        "amm": -4,
        "mam": 2,
        "mma": 8
    }

    for base_file_suffix, translation_x in base_files.items():
        base_filename = f"number_cooldown_{base_file_suffix}.json"
        # Create the base file content
        base_content = {
            "parent": "lol:base/gui_only",
            "display": {
                "gui": {
                    "translation": [translation_x, 8, 0]
                }
            }
        }

        # Write the base file
        try:
            with open(base_filename, "w") as f:
                json.dump(base_content, f, indent=4)
            print(f"Successfully created base file: {base_filename}")
        except Exception as e:
            print(f"Error writing to base file '{base_filename}': {e}")
            return  # Stop if base file creation fails

        # Generate number files (0-9) for the current base file
        for i in range(10):
            number_filename = base_filename.replace("a", f"{i}") 
            
            # Determine parent based on current base file
            parent = f"lol:gui_item_count/number_cooldown_{base_file_suffix}"

            # Create the number file content
            number_content = {
                "parent": parent,
                "textures": {
                    "layer0": f"lol:item/gui_item_count/number_{i}"
                }
            }

            # Write the number file
            try:
                with open(number_filename, "w") as f:
                    json.dump(number_content, f, indent=4)
                print(f"Successfully created: {number_filename}")
            except Exception as e:
                print(f"Error writing to file '{number_filename}': {e}")

if __name__ == "__main__":
    generate_base_and_number_files()