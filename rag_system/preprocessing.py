import os
import shutil


def preprocess_files(source_dir: str, filetypes: list[str], target_dir: str) -> None:
    """
    Finds all files with specified filetypes in a directory, flattens their path structure,
    changes their extensions to .txt, and copies them to a target directory.

    Args:
        source_dir (str): The path to the source directory to scan for files.
        filetypes (list[str]): A list of file extensions to include (e.g., ['.py', '.js']).
        target_dir (str): The path to the target directory to save the processed files.
    """
    if not os.path.exists(target_dir):
        os.makedirs(target_dir)

    # Traverse the source directory
    for root, _, files in os.walk(source_dir):
        for file in files:
            # Check if the file has one of the specified extensions
            if any(file.endswith(ft) for ft in filetypes):
                # Generate new file name
                relative_path = os.path.relpath(os.path.join(root, file), source_dir)
                new_file_name = relative_path.replace(os.sep, "_") + ".txt"
                target_path = os.path.join(target_dir, new_file_name)

                # Copy file content
                shutil.copyfile(os.path.join(root, file), target_path)


# Example usage
source_directory = "data/docs"
target_directory =  source_directory + "-processed"
extensions = [".md", ".mdx"]

preprocess_files(source_directory, extensions, target_directory)
