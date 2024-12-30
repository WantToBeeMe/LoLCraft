import json

def generate_stick_json(input_file, output_file):
    """
    Generates a stick.json file based on the structure of generate_stick.json.

    Args:
        input_file: Path to the input JSON file (generate_stick.json).
        output_file: Path to the output JSON file (stick.json).
    """
    try:
        with open(input_file, 'r') as f:
            filtered_lines = [line for line in f if not line.strip().startswith("//")]
            json_string = "".join(filtered_lines)

            # Parse the cleaned JSON string
            data = json.loads(json_string)

    except FileNotFoundError:
        print(f"Error: Input file '{input_file}' not found.")
        return

    output_data = {
        "model": {
            "type": "composite",
            "models": []
        }
    }

    # Base cases
    base_cases_model = {
        "type": "select",
        "index": 0,
        "property": "custom_model_data",
        "fallback": {
            "type": "model",
            "model": data["base_cases_fallback"]["model"]
        },
        "cases": []
    }
    for case in data["base_cases"]:
        base_cases_model["cases"].append({
            "when": case["when"],
            "model": {
                "type": "model",
                "model": case["model"]
            }
        })
    output_data["model"]["models"].append(base_cases_model)

    # Overlays
    layer_index = 1
    while True:
        layer_key = f"overlays_layer_{layer_index}"
        if layer_key in data:
            overlays_model = {
                "type": "select",
                "index": layer_index,
                "property": "custom_model_data",
                "fallback": {
                    "type": "empty"
                },
                "cases": []
            }
            for overlay in data[layer_key]:
                overlays_model["cases"].append({
                    "when": overlay["when"],
                    "model": {
                        "type": "model",
                        "model": overlay["model"]
                    }
                })
            output_data["model"]["models"].append(overlays_model)
            layer_index += 1
        else:
            break  # Exit the loop when the layer key is not found

    with open(output_file, 'w') as f:
        json.dump(output_data, f, indent="\t")

    print(f"Successfully generated '{output_file}'")

if __name__ == "__main__":
    input_json_file = "generate_stick.json"
    output_json_file = "stick.json"
    generate_stick_json(input_json_file, output_json_file)