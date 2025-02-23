
resource "aws_ecr_repository" "aws_ecr_demo_repo" {
  name                 = "aws_ecr_demo_repo"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

output "ecr_repository_url" {
  value = aws_ecr_repository.aws_ecr_demo_repo.repository_url
}