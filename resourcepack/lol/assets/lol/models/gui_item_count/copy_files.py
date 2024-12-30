import os

def print_directory_contents(ignore_files=[]):
    """
    Reads all files in the current directory, formats their contents into a 
    large string with filenames as headers, and prints the string to the console.
    Adds a separator line before and after the output.
    """

    big_string = ""

    for filename in os.listdir("."):
        if(filename in ignore_files):
            continue

        if os.path.isfile(filename):
            try:
                with open(filename, "r") as f:
                    file_contents = f.read()

                big_string += f"--- {filename} ---\n"
                big_string += file_contents
                big_string += "\n\n"

            except UnicodeDecodeError:
                print(f"Skipping file (likely binary): {filename}")
            except Exception as e:
                print(f"Error reading file {filename}: {e}")

    print("--------------------------------------------------")  # Separator line
    print(big_string)
    print("--------------------------------------------------")  # Separator line

if __name__ == "__main__":
    print_directory_contents(["copy_files.py"])